package main

import (
	"net/http"
	"os"

	"sso-system/config"
	"sso-system/handlers"
	"sso-system/middleware"
	"sso-system/pkg/db"
	"sso-system/pkg/redis"
	"sso-system/services"

	"github.com/gin-gonic/gin"
	log "github.com/sirupsen/logrus"
)

func init() {
	// 设置日志级别为 info 级别。
	// 日志级别有：Debug, Info, Warn, Error, Fatal, Panic
	log.SetLevel(log.InfoLevel)

	// 设置日志格式为 JSON 格式（可选）
	log.SetFormatter(&log.JSONFormatter{})

	// 默认情况下，日志会被写入标准错误输出。这里设置日志输出到文件或任何 io.Writer。
	// 若要将日志输出到文件，请创建一个文件对象并传递给 SetOutput 方法。
	// file, err := os.OpenFile("logfile.log", os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
	// if err == nil {
	//     log.SetOutput(file)
	// } else {
	//     log.Info("Failed to log to file, using default stderr")
	// }

	// 如果你想继续使用标准错误输出：
	log.SetOutput(os.Stderr)

	// 添加钩子（Hooks）用于在特定的日志级别触发额外的行为
	// log.AddHook(...)

	// 可以设置自定义字段作为全局字段，所有后续的日志条目都会包含这些字段。
	log.WithFields(log.Fields{
		"application": "examples-project-go",
		"environment": "production",
	}).Info("Logging system initialized.")
}

func main() {
	log.Info("程序启动")
	cfg, err := config.LoadConfig("./config")
	if err != nil {
		log.Fatalf("Error loading configuration: %v", err)
	}

	// 初始化数据库连接
	dbConn := db.NewMariadb(cfg.Database.DbUrl)
	// 在函数结束时执行，即使发生了错误或提前返回
	defer dbConn.Close()

	// 初始化 Redis 连接
	redis.InitRedis(cfg)

	router := gin.Default()

	authService := services.NewAuthService(dbConn, redis.GetRedisClient(), cfg.Security.Secret)
	userService := services.NewUserService(dbConn)

	authHandler := handlers.NewAuthHandler(authService, userService, cfg)
	userHandler := handlers.NewUserHandler(userService)

	api := router.Group("/api")
	{
		api.POST("/login", authHandler.Login)
		api.POST("/register", authHandler.Register)
		api.POST("/refreshToken", authHandler.RefreshToken)
		api.POST("/getUserByID", userHandler.GetUserByID)
		api.POST("/getUserByID", userHandler.GetUserByID)
		api.POST("/updateUser", userHandler.UpdateUser)
		api.POST("/deleteUser", userHandler.DeleteUser)
		protected := api.Group("")
		protected.Use(middleware.AuthMiddleware(authService))
		{
			protected.GET("/protected", func(c *gin.Context) {
				// 获取用户 ID
				if userID, exists := c.Get("userID"); exists {
					log.WithFields(log.Fields{
						"userID": userID,
					}).Info("Accessed protected endpoint")
					c.JSON(http.StatusOK, gin.H{
						"message": "You are authorized",
						"userID":  userID,
					})
				} else {
					c.JSON(http.StatusUnauthorized, gin.H{"error": "User ID not found"})
				}
			})
		}
	}

	// 使用 logrus 记录服务器启动信息
	log.WithFields(log.Fields{
		"port": cfg.Server.Port,
	}).Info("Server is running")

	// 使用 logrus 记录致命错误并退出程序
	if err := router.Run(":" + cfg.Server.Port); err != nil {
		log.WithError(err).Fatal("Failed to start server")
	}
	log.Info("程序结束")
}
