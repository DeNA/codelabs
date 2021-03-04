package validator

import "testing"

func TestValidatePassword(t *testing.T) {

	tests := []struct {
		name string
		args string
		want bool
	}{
		{
			name: "valid password",
			args: "abcdefg1ABCD",
			want: true,
		},
		{
			name: "invalid password: (less than 12)",
			args: "abcdefg1ABC",
			want: false,
		},
		{
			name: "invalid password: (only lowercase)",
			args: "abcdefghijkj",
			want: false,
		},
		{
			name: "valid password: (with lowercase and number)",
			args: "abcdefghijk1",
			want: true,
		},
		{
			name: "valid password: (with lowercase and uppercase)",
			args: "abcdefghijkA",
			want: true,
		},
		{
			name: "valid password: (with lowercase and uppercase)",
			args: "abcdefghijkA",
			want: true,
		},
		{
			name: "valid password: (with lowercase and symbols)",
			args: "abcdefghijk!",
			want: true,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := ValidatePassword(tt.args); got != tt.want {
				t.Errorf("ValidatePassword() = %v, want %v", got, tt.want)
			}
		})
	}
}
