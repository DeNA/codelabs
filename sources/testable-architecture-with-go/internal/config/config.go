package config

import (
	"fmt"
	"os"
	"sync"
)

var (
	appConfig *config
	once      sync.Once
)

type config struct {
	DBHost     string
	DBPort     string
	DBUserName string
	DBPassword string
	DBName     string
	Port       string
	SecretKey  string
}

func (c *config) DBSrc() string {
	return fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?parseTime=true", c.DBUserName, c.DBPassword, c.DBHost, c.DBPort, c.DBName)
}

func Config() *config {
	once.Do(func() {
		appConfig = &config{
			DBHost:     envOrDefault("DENA_GO_WS_DBHOST", "localhost"),
			DBPort:     envOrDefault("DENA_GO_WS_DBPORT", "3306"),
			DBUserName: envOrDefault("DENA_GO_WS_DBUSERNAME", "root"),
			DBPassword: envOrDefault("DENA_GO_WS_DBPASSWORD", "passw0rd"),
			DBName:     envOrDefault("DENA_GO_WS_DBNAME", "denagows2021dev"),
			Port:       envOrDefault("DENA_GO_WS_PORT", "8080"),
			SecretKey:  envOrDefault("DENA_GO_WS_SECRETKEY", "JDJhJDEwJDE5clFvMFJJdkI1T0xBSlF6ci50Ny42am84Vjd4YXYwYVN0UHFuZTF4N1ZlbzFHdjBzd0dh"),
		}
	})

	return appConfig
}

func envOrDefault(key, defaultValue string) string {
	value := os.Getenv(key)
	if value == "" {
		return defaultValue
	}
	return value
}
