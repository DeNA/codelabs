package fizzbuzz 

import (
	"strconv"
)

func Convert(n int) string {
	switch {
	case n%15 == 0:
		return "FizzBuzz"
	case n%5 == 0:
		return "Buzz"
	case n%3 == 0:
		return "Fizz"
	default:
		return strconv.Itoa(n)
	}
}
