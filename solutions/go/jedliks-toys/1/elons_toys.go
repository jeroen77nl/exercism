package elon

import "fmt"

func (car *Car) Drive() {
	if car.battery >= car.batteryDrain {
		car.battery -= car.batteryDrain
		car.distance += car.speed 
	}
}

func (car Car) DisplayDistance() string {
	return fmt.Sprintf("Driven %v meters", car.distance)
}

func (car Car) DisplayBattery() string {
	return fmt.Sprintf("Battery at %v%%", car.battery)
}

// TODO: define the 'CanFinish(trackDistance int) bool' method
func (car Car) CanFinish(trackDistance int) bool {
	timeUnits := trackDistance / car.speed
	if rest := trackDistance % car.speed; rest > 0 {
		timeUnits++
	}
	
	fmt.Println(timeUnits)
	return car.battery >= car.batteryDrain * timeUnits 
}