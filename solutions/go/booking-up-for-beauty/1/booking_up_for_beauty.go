package booking

import (
	"fmt"
	"time"
)

// Schedule returns a time.Time from a string containing a date.
func Schedule(date string) time.Time {
    layout := "1/02/2006 15:04:05"
    t, _ := time.Parse(layout,date)
	return t
}

// HasPassed returns whether a date has passed.
func HasPassed(date string) bool {
	layout := "January 2, 2006 15:04:05"
	d, _ := time.Parse(layout, date)
	return d.Before(time.Now())
	
}

// IsAfternoonAppointment returns whether a time is in the afternoon.
func IsAfternoonAppointment(date string) bool {
	layout := "Monday, January 2, 2006 15:04:05"
	d, _ := time.Parse(layout, date)
	return d.Hour() >= 12 && d.Hour() < 18
}

// Description returns a formatted string of the appointment time.
func Description(date string) string {
	layout := "1/2/2006 15:04:05"
	d, _ := time.Parse(layout, date)

	layout2 := "Monday, January 2, 2006, at 15:04"
	ds := d.Format(layout2)
	
	return fmt.Sprintf("You have an appointment on %v.", ds)
}

// AnniversaryDate returns a Time with this year's anniversary.
func AnniversaryDate() time.Time {
	stringDate := fmt.Sprintf("%v-09-15 00:00:00", time.Now().Year())
	layout := "2006-01-02 15:04:05"
	date, _ := time.Parse(layout, stringDate)
	return date
}
