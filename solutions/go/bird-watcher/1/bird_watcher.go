package birdwatcher

func TotalBirdCount(birdsPerDay []int) int {
	count := 0
	for _, birds := range birdsPerDay {
		count += birds
	}
	return count
}

func BirdsInWeek(birdsPerDay []int, week int) int {
	startAt := (week - 1) * 7
	count := 0
	for i, birds := range birdsPerDay {
		if i >= startAt && i <= startAt+6 {
			count += birds
		}
	}
	return count
}

func FixBirdCountLog(birdsPerDay []int) []int {
	for i := range birdsPerDay {
		if i%2 == 0 {
			birdsPerDay[i] += 1
		}
	}
	return birdsPerDay
}
