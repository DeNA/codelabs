package main

import (
	"fmt"
	"net/http"

	"github.com/gorilla/mux"
	"github.com/jmoiron/sqlx"

	_ "github.com/go-sql-driver/mysql"

	"github.dena.jp/swet/go-sampleapi/internal/config"
	"github.dena.jp/swet/go-sampleapi/internal/handler"
	"github.dena.jp/swet/go-sampleapi/internal/logging"
)

func main() {
	conf := config.Config()

	db := sqlx.MustConnect("mysql", conf.DBSrc())
	defer db.Close()

	logger := logging.Logger()

	r := mux.NewRouter()

	r.HandleFunc("/users", handler.PostUser(db, logger)).Methods("POST")
	r.HandleFunc("/healthz", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(200)
		w.Write([]byte("ok"))
	})

	loggerRouter := logging.Middleware(logger)(r)

	http.ListenAndServe(fmt.Sprintf(":%s", config.Config().Port), loggerRouter)
}
