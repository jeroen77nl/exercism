// Package weather provides tools for weather forcasting.
package weather

// CurrentCondition represents a weather condition.
var CurrentCondition string
// CurrentLocation represents a location.
var CurrentLocation string

// Forecast returns a formatted string showing the provided weather condition and city.
func Forecast(city, condition string) string {
	CurrentLocation, CurrentCondition = city, condition
	return CurrentLocation + " - current weather condition: " + CurrentCondition
}
