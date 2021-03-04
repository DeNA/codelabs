package model

type User struct {
	ID                int       `db:"id"`
	FirstName         string    `db:"first_name"`
	LastName          string    `db:"last_name"`
	Email             string    `db:"email"`
	PasswordHash      string    `db:"password_hash"`
	LoginFailureCount int       `db:"login_failure_count"`
}
