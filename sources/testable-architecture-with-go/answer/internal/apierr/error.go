package apierr

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
