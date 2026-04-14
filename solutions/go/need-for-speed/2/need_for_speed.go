package speed

type Car struct {
	battery      int
	batteryDrain int
	speed        int
	distance     int
}

// NewCar creates a new remote controlled car with full battery and given specifications.
func NewCar(speed, batteryDrain int) Car {
	return Car{
		battery:      100,
		batteryDrain: batteryDrain,
		speed:        speed,
		distance:     0,
	}
}

type Track struct {
	distance int
}

func NewTrack(distance int) Track {
	return Track{
		distance: distance,
	}
}

func Drive(car Car) Car {
	if car.battery < car.batteryDrain {
		return car
	}
	return Car{
		battery:      car.battery - car.batteryDrain,
		batteryDrain: car.batteryDrain,
		speed:        car.speed,
		distance:     car.distance + car.speed,
	}
}

func CanFinish(car Car, track Track) bool {
	return (track.distance/car.speed)*car.batteryDrain <= car.battery
}
