package e2e

import (
	"bytes"
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/jmoiron/sqlx"

	_ "github.com/go-sql-driver/mysql"

	"github.dena.jp/swet/go-sampleapi/internal/apierr"
	"github.dena.jp/swet/go-sampleapi/internal/config"
	"github.dena.jp/swet/go-sampleapi/internal/handler"
	"github.dena.jp/swet/go-sampleapi/internal/logging"
	"github.dena.jp/swet/go-sampleapi/internal/repository"
	"github.dena.jp/swet/go-sampleapi/internal/usecase"
)

// testで作成するuserのdata
const (
	email        = "test@example.com"
	password     = "passw0rd!123"
	passwordHash = "$2a$10$RkzG742bBehtaDamyQ3l2.mBgQ4M8iaKvso5IBr231N5B/xSVJ.0G"
)

// POST /users に対するtest
// 正常なパラメータでリクエストを行う
func Test_E2E_PostUser(t *testing.T) {
	db := sqlx.MustConnect("mysql", config.Config().DBSrc())
	defer func() {
		// DBのcleanを行う
		db.MustExec("set foreign_key_checks = 0")
		db.MustExec("truncate table users")
		db.MustExec("set foreign_key_checks = 1")
		db.Close()
	}()

	userUsecase := usecase.NewUser(repository.NewUser(), db)

	var body bytes.Buffer
	if err := json.NewEncoder(&body).Encode(&handler.ReqPostUserJSON{
		FirstName: "テスト姓",
		LastName:  "テスト名",
		Email:     email,
		Password:  password,
	}); err != nil {
		t.Fatal(err)
	}

	// requestをシュミレートする
	req := httptest.NewRequest(http.MethodPost, "/", &body)
	rec := httptest.NewRecorder()
	http.HandlerFunc(handler.PostUser(userUsecase, logging.Logger())).ServeHTTP(rec, req)

	// responseのStatus Codeをチェックする
	if rec.Code != http.StatusCreated {
		t.Errorf("status code must be 201 but: %d", rec.Code)
		t.Fatalf("body: %s", rec.Body.String())
	}

	var result handler.ResPostUserJSON
	if err := json.NewDecoder(rec.Body).Decode(&result); err != nil {
		t.Fatal(err)
	}

	// responseで返ってきたIDでuserが作られているかどうかをチェックする
	var actual string
	if err := db.Get(&actual, "select email from users where id = ?", result.ID); err != nil {
		t.Fatal(err)
	}
	if actual != email {
		t.Errorf("email must be %s but %s", email, actual)
	}
}

// POST /users に対するtest
// 重複するuserでリクエストを行う
func Test_E2E_PostUser_DuplicateEmail(t *testing.T) {
	db := sqlx.MustConnect("mysql", config.Config().DBSrc())
	defer func() {
		db.MustExec("set foreign_key_checks = 0")
		db.MustExec("truncate table users")
		db.MustExec("set foreign_key_checks = 1")
		db.Close()
	}()

	// test dataのinsertをする
	db.MustExec("insert into users(first_name, last_name, email, password_hash) values (?, ?, ?, ?)", "dummy_first_name", "dummy_last_name", email, passwordHash)

	uuc := usecase.NewUser(repository.NewUser(), db)

	var body bytes.Buffer
	if err := json.NewEncoder(&body).Encode(&handler.ReqPostUserJSON{
		FirstName: "テスト姓",
		LastName:  "テスト名",
		Email:     email,
		Password:  password,
	}); err != nil {
		t.Fatal(err)
	}

	// requestをシュミレートする
	req := httptest.NewRequest(http.MethodPost, "/", &body)
	rec := httptest.NewRecorder()
	http.HandlerFunc(handler.PostUser(uuc, logging.Logger())).ServeHTTP(rec, req)

	// response codeを確認
	if rec.Code != http.StatusBadRequest {
		t.Errorf("status code must be 400 but: %d", rec.Code)
	}

	var result handler.ResError
	if err := json.NewDecoder(rec.Body).Decode(&result); err != nil {
		t.Fatal(err)
	}

	// responseのMessageをチェックする
	if result.Message != string(apierr.ErrEmailAlreadyExists) {
		t.Errorf("error Message must be %s but %s", apierr.ErrEmailAlreadyExists, result.Message)
	}
}

// POST /users に対するtest
// validation errorのケース
func Test_E2E_PostUser_ValidationError(t *testing.T) {
	db := sqlx.MustConnect("mysql", config.Config().DBSrc())
	defer func() {
		db.MustExec("set foreign_key_checks = 0")
		db.MustExec("truncate table users")
		db.MustExec("set foreign_key_checks = 1")
		db.Close()
	}()

	uuc := usecase.NewUser(repository.NewUser(), db)

	var body bytes.Buffer

	if err := json.NewEncoder(&body).Encode(&handler.ReqPostUserJSON{
		FirstName: "name",
		LastName:  "name",
		Email:     "aaa@example.com",
		Password:  "passw0rd", // 12文字より短い
	}); err != nil {
		t.Fatal(err)
	}

	// requestをシュミレートする
	req := httptest.NewRequest("POST", "/", &body)
	rec := httptest.NewRecorder()
	http.HandlerFunc(handler.PostUser(uuc, logging.Logger())).ServeHTTP(rec, req)

	if rec.Code != http.StatusBadRequest {
		t.Errorf("status code must be 400 but: %d", rec.Code)
	}

	var result handler.ResError

	if err := json.NewDecoder(rec.Body).Decode(&result); err != nil {
		t.Fatal(err)
	}

	if result.Message != string(apierr.ErrBadRequest) {
		t.Errorf("error Message must be %s but %s", apierr.ErrBadRequest, result.Message)
	}
}
