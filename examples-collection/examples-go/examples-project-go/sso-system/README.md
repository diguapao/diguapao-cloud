# 一个单点登录（SSO）系统

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
├── cmd/
│   └── sso/
│       └── main.go
├── config/
│   └── config.go
├── handlers/
│   ├── auth_handler.go
│   └── user_handler.go
├── middleware/
│   └── auth_middleware.go
├── models/
│   └── user.go
├── pkg/
│   ├── db/
│   │   └── db.go
│   ├── redis/
│   │   └── redis.go
│   └── token/
│       └── token.go
├── services/
│   ├── auth_service.go
│   └── user_service.go
└── go.mod

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

