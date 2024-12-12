package config

import (
	"fmt"
	log "github.com/sirupsen/logrus"
	"github.com/spf13/pflag"
	"github.com/spf13/viper"
)

type Server struct {
	Port string `mapstructure:"port"`
}

type Database struct {
	Dialect     string `mapstructure:"dialect"` // 数据库方言
	DbUrl       string `mapstructure:"db_url"`
	PostgresURL string `mapstructure:"postgres_url"`
	MariaDBURL  string `mapstructure:"mariadb_url"`
}

type Redis struct {
	URL string `mapstructure:"url"`
	Pwd string `mapstructure:"pwd"`
	DB  int    `mapstructure:"db"`
}

type Security struct {
	Secret string `mapstructure:"secret"`
}

type Config struct {
	Server   Server   `mapstructure:"server"`
	Database Database `mapstructure:"database"`
	Redis    Redis    `mapstructure:"redis"`
	Security Security `mapstructure:"security"`
}

func init() {
	log.Info("解析配置文件开始")

	// 定义 pflag 标志
	pflag.String("server.port", "", "Server port")
	pflag.String("database.dialect", "", "Database dialect (postgres or mariadb)")
	pflag.String("database.postgres.url", "", "PostgreSQL URL")
	pflag.String("database.mariadb.url", "", "MariaDB URL")
	pflag.String("redis.url", "", "Redis URL")
	pflag.String("redis.pwd", "", "Redis PWD")
	pflag.Int("redis.db", -1, "Redis DB")
	pflag.String("security.secret", "", "Security secret")

	// 确保在使用 pflag 之前调用 Parse（即必须在调用任何 pflag.Lookup 之前执行）
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
	viper.BindPFlag("server.port", pflag.Lookup("server.port"))
	viper.BindPFlag("database.dialect", pflag.Lookup("database.dialect"))
	viper.BindPFlag("database.postgres_url", pflag.Lookup("database.postgres.url"))
	viper.BindPFlag("database.mariadb_url", pflag.Lookup("database.mariadb.url"))
	viper.BindPFlag("redis.url", pflag.Lookup("redis.url"))
	viper.BindPFlag("redis.pwd", pflag.Lookup("redis.pwd"))
	viper.BindPFlag("redis.db", pflag.Lookup("redis.db"))
	viper.BindPFlag("security.secret", pflag.Lookup("security.secret"))

	if err := viper.ReadInConfig(); err != nil {
		return nil, fmt.Errorf("error reading config file, %s", err)
	}

	// 解析顶层配置
	var config Config
	if err := viper.Unmarshal(&config); err != nil {
		return nil, fmt.Errorf("unable to decode into struct, %s", err)
	}

	// 解析 database 子配置
	dbSub := viper.Sub("database")
	if dbSub != nil {
		if err := dbSub.Unmarshal(&config.Database); err != nil {
			return nil, fmt.Errorf("unable to decode database config, %s", err)
		}
	} else {
		return nil, fmt.Errorf("no database configuration found")
	}

	// FIXME 不知为啥，database.postgres.url、database.mariadb.url 没获取到
	// 检查并手动设置 PostgresURL 和 MariaDBURL 如果为空
	if config.Database.PostgresURL == "" {
		config.Database.PostgresURL = viper.GetString("database.postgres.url")
	}
	if config.Database.MariaDBURL == "" {
		config.Database.MariaDBURL = viper.GetString("database.mariadb.url")
	}

	// 检查并设置 DbUrl
	dbUrl, err := config.Database.GetDbUrl()
	if err != nil {
		return nil, err
	}
	config.Database.DbUrl = dbUrl

	log.Info("读取配置完成")
	return &config, nil
}

// GetDbUrl 根据 dialect 返回正确的数据库 URL
func (db *Database) GetDbUrl() (string, error) {
	switch db.Dialect {
	case "postgres":
		if db.PostgresURL == "" {
			return "", fmt.Errorf("missing postgres URL")
		}
		dbUrl := db.PostgresURL
		db.PostgresURL = ""
		db.MariaDBURL = ""
		return dbUrl, nil
	case "mariadb":
		if db.MariaDBURL == "" {
			return "", fmt.Errorf("missing mariadb URL")
		}
		dbUrl := db.MariaDBURL
		db.PostgresURL = ""
		db.MariaDBURL = ""
		return dbUrl, nil
	default:
		return "", fmt.Errorf("invalid database dialect")
	}
}
