package usecase

import (
	"context"

	"github.com/jmoiron/sqlx"

	"github.dena.jp/swet/go-sampleapi/internal/model"
)

type userRepository interface {
	FindByEmail(ctx context.Context, queryer sqlx.QueryerContext, email string) (*model.User, error)
	Create(ctx context.Context, execer sqlx.ExecerContext, m *model.User) error
}

type User struct {
	userRepo userRepository
	db       *sqlx.DB
}

func NewUser(userRepo userRepository, db *sqlx.DB) *User {
	return &User{
		userRepo: userRepo,
		db:       db,
	}
}

func (u *User) Create(ctx context.Context, m *model.User) error {
	// TODO methodを実装する
	panic("implement me")
}
