package middleware

import (
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	log "github.com/sirupsen/logrus"
	"sso-system/services" // 处理认证逻辑的服务层
)

// AuthMiddleware 是一个 Gin 中间件，用于认证请求。
func AuthMiddleware(authService *services.AuthService) gin.HandlerFunc {
	return func(c *gin.Context) {
		// 从请求头中获取授权令牌
		authHeader := c.GetHeader("Authorization")
		if authHeader == "" {
			log.Warn("Authorization header is missing")
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Authorization header is missing"})
			c.Abort()
			return
		}

		// 检查 Authorization 头是否以 "Bearer " 开头
		parts := strings.SplitN(authHeader, " ", 2)
		if len(parts) != 2 || parts[0] != "Bearer" {
			log.Warn("Invalid authorization header format")
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid authorization header format"})
			c.Abort()
			return
		}

		token := parts[1]

		// 验证令牌
		userID, err := authService.ValidateToken(token)
		if err != nil {
			log.WithError(err).Warn("Failed to validate token")
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid or expired token"})
			c.Abort()
			return
		}

		// 将用户 ID 设置到上下文中，以便后续的处理器可以访问
		c.Set("userID", userID)

		// 继续处理链中的下一个中间件或处理器
		c.Next()
	}

}
