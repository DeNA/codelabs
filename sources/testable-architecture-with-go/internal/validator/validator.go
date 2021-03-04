// custom validatorを提供するpackageです。
// [go-playground/validator](https://github.com/go-playground/validator)を拡張する形で提供しています。
package validator

import (
	"math/bits"
	"unicode"

	"github.com/go-playground/validator/v10"
)

var Validator *validator.Validate

func init() {
	Validator = validator.New()

	Validator.RegisterValidation("password", passwordValidate)
}

func passwordValidate(fl validator.FieldLevel) bool {
	return ValidatePassword(fl.Field().String())
}

const (
	numberFlag int = 1 << iota
	lowercaseFlag
	upperCaseFlag
	symbolFlag
)

// passwordのフォーマットをチェックするvalidatorです。
// passwordのフォーマットは、12文字以上で、数値、小文字英字、大文字英字、許容される記号（! _ $ @ / - +）のうち、2種を用いているかどうかをチェックします
func ValidatePassword(password string) bool {
	if len(password) < 12 {
		return false
	}

	var flags = 0

	for _, r := range password {
		switch {
		case unicode.IsNumber(r):
			flags |= numberFlag
		case unicode.IsLower(r):
			flags |= lowercaseFlag
		case unicode.IsUpper(r):
			flags |= upperCaseFlag
		case r == '!' || r == '_' || r == '$' || r == '@' || r == '/' || r == '-' || r == '+':
			flags |= symbolFlag
		default:
			return false
		}
	}

	cnt := bits.OnesCount(uint(flags))

	return cnt > 1
}
