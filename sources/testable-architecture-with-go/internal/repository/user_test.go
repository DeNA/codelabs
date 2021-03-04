package repository

import (
	"testing"

	_ "github.com/go-sql-driver/mysql"
	"github.com/jmoiron/sqlx"

	"github.dena.jp/swet/go-sampleapi/internal/config"
)

func TestUser_FindByEmail(t *testing.T) {
	db := sqlx.MustConnect("mysql", config.Config().DBSrc())
	defer func() {
		// DBのCleanup
		db.MustExec("set foreign_key_checks = 0")
		db.MustExec("truncate table users")
		db.MustExec("set foreign_key_checks = 1")
		db.Close()
	}()

	/*
		expect := &model.User{
			Email: "test@example.com",
			PasswordHash: "dummy",
			FirstName: "dummy",
			LastName: "dummy",
		}
	*/

	// TODO: Test Fixtureの投入

	// TODO: FindByEmailを呼び出す

	// TODO: GetしたModelとexpectを比較する

}
