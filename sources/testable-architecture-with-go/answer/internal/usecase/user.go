package usecase

import (
	"context"
	"fmt"

	"github.com/jmoiron/sqlx"

	"github.dena.jp/swet/go-sampleapi/internal/apierr"
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

// repositoryとDBの依存をファクトリで受け取っている
func NewUser(userRepo userRepository, db *sqlx.DB) *User {
	return &User{
		userRepo: userRepo,
		db:       db,
	}
}

func (u *User) Create(ctx context.Context, m *model.User) error {
	_, err := u.userRepo.FindByEmail(ctx, u.db, m.Email)

	// 不明なerrorの場合はwrapしてreturn
	if err != apierr.ErrUserNotExists && err != nil {
		return fmt.Errorf("user find with email %s failed: %w", m.Email, err)
	}
	// errorが発生していない = Emailのuserが存在するということなので、errを返す
	if err == nil {
		return apierr.ErrEmailAlreadyExists
	}

	if err := u.userRepo.Create(ctx, u.db, m); err != nil {
		return fmt.Errorf("user create failed: %w", err)
	}
	return nil
}
