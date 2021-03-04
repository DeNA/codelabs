package main

import (
	"context"
	"database/sql"
	"flag"
	"log"
	"time"

	_ "github.com/go-sql-driver/mysql"

	"github.dena.jp/swet/go-sampleapi/internal/config"
)

var maxRetry = flag.Int("retry", 5, "max retry count")
var waitInterval = flag.Int("interval", 3, "retry interval (sec)")

func main() {
	flag.Parse()

	db, err := sql.Open("mysql", config.Config().DBSrc())
	if err != nil {
		log.Fatal("open failed")
	}
	defer db.Close()

	duration := time.Duration(*waitInterval) * time.Second

	var cnt int
	for range time.Tick(time.Duration(*waitInterval) * time.Second) {
		if cnt >= *maxRetry {
			log.Fatal("max retry exceeds. give up")
		}
		ctx, _ := context.WithTimeout(context.Background(), duration)

		if err := db.PingContext(ctx); err == nil {
			return
		}
		log.Println("retry")

		cnt++
	}
}
