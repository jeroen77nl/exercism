package techpalace

import (
	"fmt"
	"strings"
)

// WelcomeMessage returns a welcome message for the customer.
func WelcomeMessage(customer string) string {
	return "Welcome to the Tech Palace, " + strings.ToUpper(customer)
}

// AddBorder adds a border to a welcome message.
func AddBorder(welcomeMsg string, numStarsPerLine int) string {
	border := strings.Repeat("*", numStarsPerLine)
	return fmt.Sprintf("%v\n%v\n%v", border, welcomeMsg, border) 
}

// CleanupMessage cleans up an old marketing message.
func CleanupMessage(oldMsg string) string {
	msg := strings.Replace(oldMsg, "\n", "", -1)
	msg = strings.Replace(msg, "*", "", -1)
	return strings.TrimSpace(msg)
}
