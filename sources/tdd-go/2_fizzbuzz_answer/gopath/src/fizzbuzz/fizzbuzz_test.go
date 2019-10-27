package fizzbuzz_test

import (
	"fizzbuzz"
	"fmt"
	"testing"
)

func TestConvert(t *testing.T) {

	tests := []struct {
		n    int
		want string
	}{
		{n: 1, want: "1"},
		{n: 2, want: "2"},
		{n: 3, want: "Fizz"},
		{n: 4, want: "4"},
		{n: 5, want: "Buzz"},
		{n: 6, want: "Fizz"},
		{n: 7, want: "7"},
		{n: 8, want: "8"},
		{n: 9, want: "Fizz"},
		{n: 10, want: "Buzz"},
		{n: 11, want: "11"},
		{n: 12, want: "Fizz"},
		{n: 13, want: "13"},
		{n: 14, want: "14"},
		{n: 15, want: "FizzBuzz"},
	}

	for _, tt := range tests {
		tt := tt
		name := fmt.Sprintf("number:%v", tt.n)

		t.Run(name, func(t *testing.T) {
			t.Parallel()
			testFizzBuzz(t, tt.n, tt.want)
		})
	}
}

func testFizzBuzz(t *testing.T, n int, want string) {
	t.Helper()
	got := fizzbuzz.Convert(n)
	if got != want {
		t.Errorf(`Convert(%v) = %q but want %q`, n, got, want)
	}
}
