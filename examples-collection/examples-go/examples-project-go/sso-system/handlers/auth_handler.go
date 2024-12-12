package handlers

import (
	"net/http"
	"sso-system/services"

	"github.com/gin-gonic/gin"
	log "github.com/sirupsen/logrus"
)

type AuthHandler struct {
	authService *services.AuthService
}

// NewAuthHandler 创建一个新的 AuthHandler 实例
func NewAuthHandler(authService *services.AuthService) *AuthHandler {
	return &AuthHandler{
		authService: authService,
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
	userID, err := ah.authService.CreateUser(registerReq.Username, registerReq.Email, registerReq.Password)
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
	// 实现刷新令牌逻辑...
}
