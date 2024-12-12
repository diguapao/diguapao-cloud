package config

import (
	"fmt"
	log "github.com/sirupsen/logrus"
	"github.com/spf13/pflag"
	"github.com/spf13/viper"
)

type Config struct {
	Port     string
	DBURL    string
	RedisURL string
	RedisPWD string
	RedisDB  int
	Secret   string
}

func init() {
	log.Info("解析配置文件开始")
	// 定义 pflag 标志
	pflag.String("port", "8080", "Server port")
	pflag.String("db-url", "", "Database URL")
	pflag.String("redis-url", "", "Redis URL")
	pflag.String("redis-pwd", "", "Redis PWD")
	pflag.String("redis-DB", "", "Redis DB")
	pflag.String("secret", "", "Security secret")
	// 确保在使用 pflag 之前调用 Parse
	pflag.Parse()
	log.Info("解析配置文件完成")
}

func LoadConfig(path string) (*Config, error) {
	log.Info("读取配置开始：" + path)
	viper.AddConfigPath(path)
	viper.SetConfigName("config")
	viper.SetConfigType("yaml")

	// 优先从环境变量和命令行参数读取配置
	viper.AutomaticEnv()
	viper.SetEnvPrefix("app") // 设置环境变量前缀为 APP_

	// 绑定命令行参数到 Viper
	viper.BindPFlag("server.port", pflag.Lookup("port"))
	viper.BindPFlag("database.url", pflag.Lookup("db-url"))
	viper.BindPFlag("redis.url", pflag.Lookup("redis-url"))
	viper.BindPFlag("security.secret", pflag.Lookup("secret"))

	if err := viper.ReadInConfig(); err != nil {
		return nil, fmt.Errorf("error reading config file, %s", err)
	}

	var config Config
	if err := viper.Unmarshal(&config); err != nil {
		return nil, fmt.Errorf("unable to decode into struct, %s", err)
	}

	log.Info("读取配置完成")
	return &config, nil
}
