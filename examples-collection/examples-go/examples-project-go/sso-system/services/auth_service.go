package services

import (
	"database/sql"
	"errors"
	"fmt"
	"github.com/go-redis/redis/v8"
	"github.com/lestrrat-go/jwx/v2/jwa"
	"github.com/lestrrat-go/jwx/v2/jwk"
	"github.com/lestrrat-go/jwx/v2/jws"
	"github.com/lestrrat-go/jwx/v2/jwt"
	log "github.com/sirupsen/logrus"
	"golang.org/x/crypto/bcrypt"
	"strconv"
	"strings"
	"time"
)

type AuthService struct {
	db     *sql.DB
	redis  *redis.Client
	secret string
}

func NewAuthService(db *sql.DB, redisClient *redis.Client, secret string) *AuthService {
	return &AuthService{
		db:     db,
		redis:  redisClient,
		secret: secret,
	}
}

// Login 验证用户凭据并返回JWT令牌
func (s *AuthService) Login(username string, password string) (string, error) {
	var user User
	err := s.db.QueryRow("SELECT id, username, password FROM users WHERE username = $1", username).Scan(&user.ID, &user.Username, &user.Password)
	if err != nil {
		if err == sql.ErrNoRows {
			return "", errors.New("invalid credentials")
		}
		return "", fmt.Errorf("database query failed: %w", err)
	}

	// 检查密码是否匹配
	err = bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(password))
	if err != nil {
		return "", errors.New("invalid credentials")
	}

	claims := map[string]interface{}{
		"sub":  user.ID,                               // 用户 ID
		"exp":  time.Now().Add(24 * time.Hour).Unix(), // 令牌有效期为24小时
		"role": user.Role,                             // 用户角色
	}

	// 构建JWT令牌
	token, err := jwt.NewBuilder().
		Issuer(`sso-system`).
		IssuedAt(time.Now()).
		Claim("claims", claims).
		Build()
	if err != nil {
		return "", fmt.Errorf("failed to build token: %w", err)
	}

	//创建对称签名的key
	key, err := jwk.FromRaw([]byte(s.secret))
	if err != nil {
		return "", fmt.Errorf(`failed to create new symmetric key: %s`, err)
	}
	key.Set(jws.KeyIDKey, s.secret)

	//签名令牌：使用HS256对称签名方式进行签名，生成 JWS
	signedToken, err := jwt.Sign(token, jwt.WithKey(jwa.HS256, key))
	if err != nil {
		return "", fmt.Errorf("failed to sign token: %w", err)
	}

	return string(signedToken), nil
}

// ValidateToken 验证给定的令牌字符串并返回用户 ID 和错误
func (s *AuthService) ValidateToken(tokenStr string) (int, error) {
	// 解析 JWT 令牌
	token, err := jwt.Parse([]byte(tokenStr), jwt.WithKey(jwa.HS256, []byte(s.secret)))
	if err != nil {
		return 0, fmt.Errorf("failed to parse token: %w", err)
	}

	claims := token.PrivateClaims()
	log.Info("Error parsing JSON string: %v", claims)

	userId := token.Subject()
	// 检查 userId 是否为空或仅包含空白字符
	if len(strings.TrimSpace(userId)) == 0 {
		// userID 是空的或者只包含空白字符
		return 0, fmt.Errorf("invalid user ID in token")
	}

	userIdInt, err := strconv.Atoi(userId)

	return userIdInt, nil
}
