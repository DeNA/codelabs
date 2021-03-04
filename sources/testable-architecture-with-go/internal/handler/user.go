package handler

import (
	"database/sql"
	"encoding/json"
	"net/http"

	"github.com/jmoiron/sqlx"
	"github.com/sirupsen/logrus"
	"golang.org/x/crypto/bcrypt"

	"github.dena.jp/swet/go-sampleapi/internal/validator"
)

type ReqPostUserJSON struct {
	FirstName string `json:"first_name" validate:"required,max=256,min=1"`
	LastName  string `json:"last_name" validate:"required,max=256,min=1"`
	Email     string `json:"email" validate:"required,email"`
	Password  string `json:"password" validate:"required,password"`
}

type ResPostUserJSON struct {
	ID        int    `json:"id"`
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
	Email     string `json:"email"`
}

// PostUser userを新規登録するAPI
func PostUser(db *sqlx.DB, logger *logrus.Logger) http.HandlerFunc {
	return func(w http.ResponseWriter, r * http.Request) {
		ctx := r.Context()

		// 1. jsonからGoのstructへのbind
		var user ReqPostUserJSON
		if err := json.NewDecoder(r.Body).Decode(&user); err != nil {
			logger.Warnf("json decode failed: %+v", err)
			writeError(w, http.StatusBadRequest, ErrBadRequest)
			return
		}

		// 2. validation
		if err := validator.Validator.Struct(&user); err != nil {
			writeError(w, http.StatusBadRequest, ErrBadRequest)
			return
		}

		hash, err := bcrypt.GenerateFromPassword([]byte(user.Password), bcrypt.DefaultCost)
		if err != nil {
			logger.Errorf("bcrypt error: %+v", err)
			writeError(w, http.StatusInternalServerError, ErrInternalServerError)
			return
		}

		/* TODO: 以下の処理をusecaseとrepositoryに移す */

		// 3. データが正当かどうかのチェックを行う
		// emailによりuserの存在チェックを行う
		var id int
		err = db.GetContext(ctx, &id, "SELECT id from users where email = ?", user.Email)

		if err != nil && err != sql.ErrNoRows { // sql.ErrNoRows以外のerrorが発生しているケース
			logger.Warnf("select failed: %+v", err)
			writeError(w, http.StatusInternalServerError, ErrInternalServerError)
			return
		} else if err == nil { // errが発生していないケース、つまりuserが存在しているケース
			writeError(w, http.StatusBadRequest, ErrEmailAlreadyExists)
			return
		}

		// 4. DBへのinsert
		rs, err := db.ExecContext(ctx, "INSERT INTO users(first_name, last_name, email, password_hash) VALUES (?, ?, ?, ?)", user.FirstName, user.LastName, user.Email, string(hash))
		if err != nil {
			logger.Errorf("insert failed: %+v", err)
			writeError(w, http.StatusInternalServerError, ErrInternalServerError)
			return
		}

		// responseにUserIDを返すために、ResultSetからIDを取得する
		insertedId, err := rs.LastInsertId()
		if err != nil {
			logger.Errorf("lastInsertId failed: %+v", err)
			writeError(w, http.StatusInternalServerError, ErrInternalServerError)
			return
		}
		/* ここまで */

		// 5. http responseの作成とwrite

		w.Header().Set("Content-Type", "application/json;charset=utf-8")
		w.WriteHeader(http.StatusCreated)

		if err := json.NewEncoder(w).Encode(&ResPostUserJSON{
			ID:        int(insertedId),
			FirstName: user.FirstName,
			LastName:  user.LastName,
			Email:     user.Email,
		}); err != nil {
			logger.Errorf("json encode failed: %v", err)
		}
	}
}
