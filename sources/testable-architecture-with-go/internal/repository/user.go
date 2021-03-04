package repository

import (
	"context"

	"github.com/jmoiron/sqlx"

	"github.dena.jp/swet/go-sampleapi/internal/model"
)

type User struct{}

func NewUser() *User {
	return &User{}
}

func (u *User) FindByEmail(ctx context.Context, queryer sqlx.QueryerContext, email string) (*model.User, error) {
	// TODO methodを実装する
	panic("implement me")
}

func (u *User) Create(ctx context.Context, execer sqlx.ExecerContext, m *model.User) error {
	// TODO methodを実装する
	panic("implement me")
}
