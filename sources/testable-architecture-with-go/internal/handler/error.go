package handler

import (
	"encoding/json"
	"net/http"
)

// TODO: APIErrorの定義を削除する

type APIError string

func (a APIError) Error() string {
	return string(a)
}

const (
	ErrBadRequest          APIError = "不正なリクエストです"
	ErrInternalServerError APIError = "サーバでエラーが発生しました"
	ErrEmailAlreadyExists  APIError = "すでに登録されています"
	ErrUserNotExists       APIError = "ユーザは存在しません"
)

type ResError struct {
	Message string `json:"message"`
}

func writeError(w http.ResponseWriter, code int, err error) {
	w.Header().Set("Content-Type", "application/json;charset=utf-8")
	w.WriteHeader(code)
	resp := &ResError{Message: err.Error()}
	json.NewEncoder(w).Encode(resp)
}
