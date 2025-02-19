# 一个单基于 Golang 开发点登录（SSO）系统

## 技术栈

- Gin：一个高性能的 HTTP Web 框架。

- go-oauth2-server 或 fosite：用于实现 OAuth2 和 OpenID Connect 的服务器端逻辑。

- jwt-go：用于生成和验证 JWT。

- Redis：作为会话和令牌的缓存存储。

- sqlx：用于数据库访问（例如 PostgreSQL 或 MySQL），以便持久化用户数据。

- go-oidc：用于 OpenID Connect 客户端。

## 项目结构

```
sso-system/
├── cmd/                    应用程序启动的地方
│   └── sso/                
│       └── main.go         程序的主文件，负责初始化配置、设置路由和启动 HTTP 服务器
├── config/                 集中管理应用配置
│   └── config.go           定义配置结构体，并提供加载配置的方法（例如从环境变量、配置文件等）
├── handlers/               负责接收和响应 HTTP 请求
│   ├── auth_handler.go     处理与认证相关的 HTTP 请求，如登录、注册、刷新令牌等
│   └── user_handler.go     处理用户管理相关的 HTTP 请求，如获取用户信息、更新用户资料等
├── middleware/             处理跨多个处理器的通用逻辑
│   └── auth_middleware.go  实现认证中间件，如检查访问令牌的有效性，保护需要授权的端点
├── models/                 定义应用程序的数据结构
│   └── user.go             定义 User 结构体，表示用户的属性和行为
├── pkg/                    包含各种可复用的功能组件
│   ├── db/
│   │   └── db.go           提供数据库连接管理和操作方法
│   ├── redis/
│   │   └── redis.go        提供 Redis 连接管理和操作方法
│   └── token/
│       └── token.go        实现 JWT 签发、验证等功能
├── services/               核心业务规则和服务的实现
│   ├── auth_service.go     包含认证相关的业务逻辑，如用户登录、密码重置等
│   └── user_service.go     包含用户管理相关的业务逻辑，如创建用户、查询用户信息等
└── go.mod                  Go 模块文件，定义了项目的模块名称以及所有直接和间接依赖的外部库版本

```

## 初始化项目

```shell

mkdir sso-system && cd sso-system
go mod init sso-system
go get -u github.com/gin-gonic/gin
go get -u github.com/lestrrat-go/jwx/v2/jwt
go get -u github.com/lestrrat-go/jwx/v2/jwk
go get -u github.com/ory/fosite
go get -u github.com/go-redis/redis/v8
go get -u github.com/jmoiron/sqlx
go get -u github.com/ory/hydra-client-go
go get -u github.com/spf13/viper
go get -u github.com/sirupsen/logrus
go get -u github.com/go-sql-driver/mysql

```

## 启动项目

```shell
go run cmd/sso/main.go -port=8080 -db-url="postgres://user:password@localhost/dbname?sslmode=disable" -redis-url="redis://localhost:6379" -secret="my_secret_key"
```

# 可能遇到的问题

## 依赖无法下载

```shell
go env -w GOPROXY=https://goproxy.cn,direct
```

