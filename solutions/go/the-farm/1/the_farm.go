package thefarm

import (
	"errors"
	"fmt"
)

// TODO: define the 'DivideFood' function
func DivideFood(fc FodderCalculator, cows int) (float64, error) {
	totalAmoutOfFodder, err := fc.FodderAmount(cows)
	if err != nil {
		return 0, err
	}
	fatteningFactor, err := fc.FatteningFactor()
	if err != nil {
		return 0, err
	}
	return (totalAmoutOfFodder * fatteningFactor) / float64(cows), nil
}

func ValidateInputAndDivideFood(fc FodderCalculator, cows int) (float64, error) {
	if cows <= 0 {
		return 0, errors.New("invalid number of cows")
	}
	return DivideFood(fc, cows)
}

type InvalidCowsError struct {
	cows    int
	message string
}

func (e *InvalidCowsError) Error() string {
	return fmt.Sprintf("%v cows are invalid: %v", e.cows, e.message)
}

func ValidateNumberOfCows(cows int) *InvalidCowsError {
	if cows < 0 {
		return &InvalidCowsError{
			cows:    cows,
			message: "there are no negative cows",
		}
	}
	if cows == 0 {
		return &InvalidCowsError{
			cows:    cows,
			message: "no cows don't need food",
		}
	}
	return nil
}
