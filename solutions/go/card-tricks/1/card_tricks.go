package cards

func FavoriteCards() []int {
	return []int{2, 6, 9}
}

func GetItem(slice []int, index int) int {
	if !indexInBounds(slice, index) {
		return -1
	}
	return slice[index]
}

func SetItem(slice []int, index, value int) []int {
	if !indexInBounds(slice, index) {
		return append(slice, value)
	}

	slice[index] = value
	return slice
}

func PrependItems(slice []int, values ...int) []int {
	return append(values, slice...)
}

func RemoveItem(slice []int, index int) []int {
	if !indexInBounds(slice, index) {
		return slice
	}

	ret := []int{}
	ret = append(ret, slice[:index]...)
	return append(ret, slice[index+1:]...)
}

func indexInBounds(slice[] int, index int) bool {
	return index >= 0 && index < len(slice)
}