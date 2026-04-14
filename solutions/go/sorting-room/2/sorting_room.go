package sorting

import (
	"fmt"
	"strconv"
)

func DescribeNumber(f float64) string {
	return fmt.Sprintf("This is the number %.1f", f)
}

type NumberBox interface {
	Number() int
}

func DescribeNumberBox(nb NumberBox) string {
	return fmt.Sprintf("This is a box containing the number %.1f", float64(nb.Number()))
}

type FancyNumber struct {
	n string
}

func (i FancyNumber) Value() string {
	return i.n
}

type FancyNumberBox interface {
	Value() string
}

func stringToIntDefault0(number string) int {
	i, err := strconv.Atoi(number)
	if err != nil {
		return 0
	}
	return i
}

func ExtractFancyNumber(fnb FancyNumberBox) int {
	if value, ok := fnb.(FancyNumber); ok {
		return stringToIntDefault0(value.Value())
	} else {
		return 0
	}
}

func DescribeFancyNumberBox(fnb FancyNumberBox) string {
	display := 0.0
	if value, ok := fnb.(FancyNumber); ok {
		display = float64(stringToIntDefault0(value.Value()))
	}
	return fmt.Sprintf("This is a fancy box containing the number %.1f", display)
}

func DescribeAnything(i interface{}) string {
	switch v := i.(type) {
	case int:
		return DescribeNumber(float64(v))
	case float64:
		return DescribeNumber(v)
	case NumberBox:
		return DescribeNumberBox(v)
	case FancyNumberBox:
		return DescribeFancyNumberBox(v)
	default:
		return "Return to sender"
	}
}
