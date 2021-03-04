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
			DBHost:     envOrDefault("CAMPHORWS_DBHOST", "localhost"),
			DBPort:     envOrDefault("CAMPHORWS_DBPORT", "3306"),
			DBUserName: envOrDefault("CAMPHORWS_DBUSERNAME", "root"),
			DBPassword: envOrDefault("CAMPHORWS_DBPASSWORD", "passw0rd"),
			DBName:     envOrDefault("CAMPHORWS_DBNAME", "camphorws2021dev"),
			Port:       envOrDefault("CAMPHORWS_PORT", "8080"),
			SecretKey:  envOrDefault("CAMPHORWS_SECRETKEY", "JDJhJDEwJDE5clFvMFJJdkI1T0xBSlF6ci50Ny42am84Vjd4YXYwYVN0UHFuZTF4N1ZlbzFHdjBzd0dh"),
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
