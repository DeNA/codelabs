package repository

import (
	"context"
	"testing"

	"github.com/jmoiron/sqlx"

	_ "github.com/go-sql-driver/mysql"

	"github.dena.jp/swet/go-sampleapi/internal/config"
	"github.dena.jp/swet/go-sampleapi/internal/model"
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

	expect := &model.User{
		Email:        "test@example.com",
		PasswordHash: "dummy",
		FirstName:    "dummy",
		LastName:     "dummy",
	}

	// Test Fixtureの投入
	db.MustExec("insert into users(first_name, last_name, email, password_hash) values (?, ?, ?, ?)",
		expect.FirstName, expect.LastName, expect.Email, expect.PasswordHash)

	// FindByEmailを呼び出す
	repo := NewUser()
	u, err := repo.FindByEmail(context.Background(), db, "test@example.com")
	if err != nil {
		t.Fatal(err)
	}

	// GetしたModelとexpectを比較する

	if u.Email != expect.Email {
		t.Errorf("email must be %s but %s", expect.Email, u.Email)
	}
	if u.PasswordHash != expect.PasswordHash {
		t.Errorf("password_hash must be %s but %s", expect.PasswordHash, u.PasswordHash)
	}
	if u.FirstName != expect.FirstName {
		t.Errorf("first_name must be %s but %s", expect.FirstName, u.FirstName)
	}
	if u.LastName != expect.LastName {
		t.Errorf("last_name must be %s but %s", expect.LastName, u.LastName)
	}

}
