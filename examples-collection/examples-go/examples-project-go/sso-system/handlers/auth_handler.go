package handlers

import (
	"fmt"
	"github.com/lestrrat-go/jwx/v2/jwa"
	"github.com/lestrrat-go/jwx/v2/jws"
	"github.com/lestrrat-go/jwx/v2/jwt"
	log "github.com/sirupsen/logrus"
	"net/http"
	"sso-system/config"
	"sso-system/services"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
)

type AuthHandler struct {
	authService *services.AuthService
	userService *services.UserService
	cfg         *config.Config
}

// Claims 代表自定义的 JWT 声明
type Claims struct {
	Type string `json:"type"`
	jwt.ClaimPair
}

// TokenResponse 是返回给客户端的令牌响应结构
type TokenResponse struct {
	AccessToken  string `json:"access_token"`
	RefreshToken string `json:"refresh_token,omitempty"` // 可选的新刷新令牌
}

// NewAuthHandler 创建一个新的 AuthHandler 实例
func NewAuthHandler(authService *services.AuthService, userService *services.UserService, cfg *config.Config) *AuthHandler {
	return &AuthHandler{
		authService: authService,
		userService: userService,
		cfg:         cfg,
	}
}

// Login 登录接口
func (ah *AuthHandler) Login(c *gin.Context) {
	var loginReq struct {
		Username string `json:"username" binding:"required"`
		Password string `json:"password" binding:"required"`
	}

	if err := c.ShouldBindJSON(&loginReq); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// 调用 AuthService 进行登录验证
	token, err := ah.authService.Login(loginReq.Username, loginReq.Password)
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "invalid credentials"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"token": token})
}

// Register 注册接口
func (ah *AuthHandler) Register(c *gin.Context) {
	var registerReq struct {
		Username string `json:"username" binding:"required"`
		Email    string `json:"email" binding:"required,email"`
		Password string `json:"password" binding:"required"`
	}

	if err := c.ShouldBindJSON(&registerReq); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// 调用 UserService 创建新用户
	user := &services.User{
		Username: registerReq.Username,
		Password: registerReq.Password,
		Email:    registerReq.Email,
	}
	userID, err := ah.userService.CreateUser(user)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	// 如果没有错误，则记录成功的日志，包括用户 ID
	log.WithFields(log.Fields{
		"userID": userID,
	}).Info("New user created successfully")

	c.JSON(http.StatusCreated, gin.H{"message": "user created"})
}

// RefreshToken 刷新令牌接口
func (ah *AuthHandler) RefreshToken(c *gin.Context) {
	var req struct {
		RefreshToken string `json:"refresh_token" binding:"required"`
	}
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid request"})
		return
	}

	// 解析刷新令牌
	parsedToken, err := jwt.Parse([]byte(req.RefreshToken))
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid refresh token"})
		return
	}

	// 验证刷新令牌
	// 假设你有一个全局配置对象 config.Config，其中包含用于签名的 JWK 或对称密钥
	key := ah.cfg.Security.Secret // 对称密钥示例

	verifier := jws.NewVerifier(jwa.HS256, key)
	if !parsedToken.VerifySignature(verifier) {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid token signature"})
		return
	}

	// 提取声明
	claims := parsedToken.PrivateClaims()
	if claims.Type != "refresh" {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid token type"})
		return
	}

	// 获取用户信息（假设 UserService 提供了这种方法）
	userId, err := strconv.Atoi(parsedToken.Subject())
	user, err := ah.userService.GetUserByID(userId)
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "User not found"})
		return
	}

	// 生成新的访问令牌
	newAccessToken, err := generateAccessToken(user, ah.cfg)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to generate access token"})
		return
	}

	// （可选）生成新的刷新令牌
	var newRefreshToken string
	if shouldGenerateNewRefreshToken() { // 根据业务逻辑判断是否生成新的刷新令牌
		newRefreshToken, err = generateRefreshToken(user, ah.cfg)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to generate refresh token"})
			return
		}
	}

	// 返回新令牌给客户端
	resp := TokenResponse{
		AccessToken:  newAccessToken,
		RefreshToken: newRefreshToken, // 可能为空
	}
	c.JSON(http.StatusOK, resp)
}

// generateAccessToken 生成新的访问令牌
func generateAccessToken(user *services.User, cfg *config.Config) (string, error) {
	// 构建JWT令牌
	token, err := jwt.NewBuilder().
		Issuer(`sso-system`).
		Audience([]string{`your_client`}).
		Subject(fmt.Sprintf("%d", user.ID)).
		Claim("username", user.Username).
		Claim("type", "access").
		Expiration(time.Now().Add(time.Hour)). // 访问令牌的有效期
		Build()
	if err != nil {
		return "", fmt.Errorf("failed to build token: %w", err)
	}

	if err != nil {
		return "", err
	}

	// 签名令牌
	signedToken, err := jwt.Sign(token, jwt.WithKey(jwa.HS256, cfg.Security.Secret))
	if err != nil {
		return "", err
	}

	return string(signedToken), nil
}

// generateRefreshToken 生成新的刷新令牌
func generateRefreshToken(user *services.User, cfg *config.Config) (string, error) {
	builder := jwt.NewBuilder()
	token, err := builder.
		Issuer("your_service").
		Audience([]string{`your_client`}).
		Subject(fmt.Sprintf("%d", user.ID)).
		Claim("username", user.Username).
		Claim("type", "refresh").
		Expiration(time.Now().Add(7 * 24 * time.Hour)). // 刷新令牌的有效期
		Build()

	if err != nil {
		return "", err
	}

	// 签名令牌
	signedToken, err := jwt.Sign(token, jwt.WithKey(jwa.HS256, cfg.Security.Secret))
	if err != nil {
		return "", err
	}

	return string(signedToken), nil
}

// shouldGenerateNewRefreshToken 决定是否生成新的刷新令牌
func shouldGenerateNewRefreshToken() bool {
	// 这里可以添加你的业务逻辑来决定是否生成新的刷新令牌
	return true // 默认情况下总是生成新的刷新令牌
}
