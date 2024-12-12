package redis

import (
	"context"
	"log"
	"sso-system/config"

	"github.com/go-redis/redis/v8"
)

var rdb *redis.Client

func InitRedis(cfg *config.Config) {
	rdb = redis.NewClient(&redis.Options{
		Addr:     cfg.RedisURL,
		Password: cfg.RedisPWD,
		DB:       cfg.RedisDB,
	})

	ctx := context.Background()
	_, err := rdb.Ping(ctx).Result()
	if err != nil {
		log.Fatalf("Failed to connect to Redis: %v", err)
	}
}

func GetRedisClient() *redis.Client {
	return rdb
}
