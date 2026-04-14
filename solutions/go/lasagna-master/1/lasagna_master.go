package lasagna

func PreparationTime(layers []string, avgPrepTimePerLayer int) int {
	if avgPrepTimePerLayer == 0 {
		avgPrepTimePerLayer = 2
	}
	return len(layers) * avgPrepTimePerLayer
}

func Quantities(layers []string) (int, float64) {
	countSauce := 0.0
	countNoodles := 0
	for _, layer := range layers {
		switch layer {
		case "sauce":
			countSauce += 0.2;
		case "noodles":
			countNoodles += 50;
		}
	}
	return countNoodles, countSauce
}

// TODO: define the 'AddSecretIngredient()' function
func AddSecretIngredient(friends []string, mine []string) {
	mine[len(mine)-1] = friends[len(friends)-1]
}

// TODO: define the 'ScaleRecipe()' function
func ScaleRecipe(quantities []float64, portions int) []float64{
	result := []float64{}
	for _, quantity := range quantities {
		result = append(result, quantity * float64(portions) / 2)
	}
	return result
}

// Your first steps could be to read through the tasks, and create
// these functions with their correct parameter lists and return types.
// The function body only needs to contain `panic("")`.
// 
// This will make the tests compile, but they will fail.
// You can then implement the function logic one by one and see
// an increasing number of tests passing as you implement more 
// functionality.
