package forth

import (
	"errors"
	"fmt"
	"strconv"
	"strings"
)

var stack []int

var symbolMapping = make(map[string][]string, 8)
var extendedSymbolMapping map[string][]string

func initSymbolMapping() {
	symbolMapping["+"] = []string{"+"}
	symbolMapping["-"] = []string{"-"}
	symbolMapping["*"] = []string{"*"}
	symbolMapping["/"] = []string{"/"}
	symbolMapping["dup"] = []string{"dup"}
	symbolMapping["drop"] = []string{"drop"}
	symbolMapping["swap"] = []string{"swap"}
	symbolMapping["over"] = []string{"over"}
}

func Forth(input []string) ([]int, error) {
	stack = []int{}
	initSymbolMapping()
	extendedSymbolMapping = make(map[string][]string)

	for _, block := range input {
		block = strings.ToLower(block)
		symbols := strings.Split(block, " ")
		if isDefinition(symbols) {
			err := processDefinition(symbols)
			if err != nil {
				return nil, err
			}
		} else {
			err := process(symbols)
			if err != nil {
				return stack, err
			}
		}
	}
	return stack, nil
}

func process(symbols []string) error {
	for _, symbol := range symbols {
		if number, isNumber := stringToInt(symbol); isNumber {
			stack = append(stack, int(number))
		} else {
			actions, err := bepaalActions(symbol)
			if err != nil {
				return err
			}
			for _, action := range actions {
				if number, isNumber := stringToInt(action); isNumber {
					stack = append(stack, int(number))
				} else {
					err := processAction(action)
					if err != nil {
						return err
					}
				}
			}
		}
	}
	return nil
}

func bepaalActions(symbol string) ([]string, error) {
	if extendedActions, found := extendedSymbolMapping[symbol]; found {
		return extendedActions, nil
	}
	if actions, found := symbolMapping[symbol]; found {
		return actions, nil
	}
	return nil, fmt.Errorf("unknown symbol %v", symbol)
}

func processAction(action string) error {
	switch action {
	case "+", "-", "*", "/", "swap", "over":
		err := binaryOperation(action)
		if err != nil {
			return err
		}
	case "dup", "drop":
		err := unaryOperation(action)
		if err != nil {
			return err
		}
	}
	return nil
}

func binaryOperation(action string) error {
	val1, err := pop()
	if err != nil {
		return err
	}
	val2, err := pop()
	if err != nil {
		return err
	}
	switch action {
	case "+":
		push(val2 + val1)
	case "-":
		push(val2 - val1)
	case "*":
		push(val2 * val1)
	case "/":
		if val1 == 0 {
			return errors.New("divide by zero")
		}
		push(val2 / val1)
	case "swap":
		push(val1)
		push(val2)
	case "over":
		push(val2)
		push(val1)
		push(val2)
	}
	return nil
}

func unaryOperation(action string) error {
	val1, err := pop()
	if err != nil {
		return err
	}
	switch action {
	case "dup":
		push(val1)
		push(val1)
	case "drop":
	}
	return nil
}

func pop() (int, error) {
	if len(stack) == 0 {
		return 0, errors.New("empty stack error")
	}
	number := stack[len(stack)-1]
	stack = stack[:len(stack)-1]
	return number, nil
}

func push(value int) {
	stack = append(stack, value)
}

func processDefinition(tokens []string) error {
	pureTokens := tokens[1 : len(tokens)-1]
	if len(pureTokens) <= 1 {
		return errors.New("defined word contains no actions")
	}

	key := pureTokens[0]
	if _, isNumber := stringToInt(key); isNumber {
		return errors.New("numerieke waarde mag geen key zijn")
	}

	symbols := pureTokens[1:]

	actionsInDefinition := []string{}
	for _, symbol := range symbols {
		if _, isNumber := stringToInt(symbol); isNumber {
			actionsInDefinition = append(actionsInDefinition, symbol)
		} else {
			actions, err := bepaalActions(symbol)
			if err != nil {
				return err
			}
			actionsInDefinition = append(actionsInDefinition, actions...)
		}
	}
	extendedSymbolMapping[key] = actionsInDefinition
	return nil
}

func stringToInt(s string) (int, bool) {
	number, err := strconv.ParseInt(s, 10, 32)
	if err != nil {
		return 0, false
	}
	return int(number), true
}

func isDefinition(symbols []string) bool {
	return symbols[0] == ":" && symbols[len(symbols) - 1] == ";"
}
