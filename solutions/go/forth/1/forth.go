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
		fmt.Println(block)
		if strings.HasPrefix(block, ":") && strings.HasSuffix(block, ";") {
			err := processDefinition(block)
			if err != nil {
				return nil, err
			}
		} else {
			err := process(block)
			if err != nil {
				return stack, err
			}
		}
	}

	fmt.Println(stack)
	return stack, nil
}

func process(block string) error {
	fmt.Println("process", block)
	symbols := strings.Split(block, " ")
	for _, symbol := range symbols {
		if number, err := strconv.ParseInt(symbol, 10, 32); err == nil {
			stack = append(stack, int(number))
		} else {
			actions, err := bepaalActions(symbol)
			if err != nil {
				return err
			}
			for _, action := range actions {
				if number, err := strconv.ParseInt(action, 10, 32); err == nil {
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
	extendedActions, found := extendedSymbolMapping[symbol]
	if found {
		return extendedActions, nil
	}
	actions, found := symbolMapping[symbol]
	if found {
		return actions, nil
	}
	return nil, fmt.Errorf("unknown symbol %v", symbol)
}

func processAction(action string) error {
	fmt.Println("processAction", action)
	switch action {
	case "+", "-", "*", "/", "swap", "over":
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
	case "dup":
		val1, err := pop()
		fmt.Println(val1, err)
		if err != nil {
			return err
		}
		push(val1)
		push(val1)
		fmt.Println(stack)
	case "drop":
		_, err := pop()
		if err != nil {
			return err
		}
	}
	return nil
}

func pop() (int, error) {
	if len(stack) > 0 {
		number := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		return number, nil
	} else {
		return 0, errors.New("empty stack error")
	}
}

func push(value int) {
	stack = append(stack, value)
}

func processDefinition(definition string) error {
	fmt.Println("processDefinition")
	symbols := strings.Split(definition, " ")
	symbols = symbols[1 : len(symbols)-1]
	if len(symbols) <= 1 {
		return errors.New("defined word contains no actions")
	}
	key := symbols[0]
	symbols = symbols[1:]
	fmt.Println("key", key)
	fmt.Println(symbols)
	if _, err := strconv.ParseInt(key, 10, 32); err == nil {
		return errors.New("numerieke waarde mag geen key zijn")
	}
	actionsInDefinition := []string{}
	for _, symbol := range symbols {
		fmt.Println("symbol", symbol)
		if _, err := strconv.ParseInt(symbol, 10, 32); err == nil {
			actionsInDefinition = append(actionsInDefinition, symbol)
		} else {
			actions, err := bepaalActions(symbol)
			if err != nil {
				fmt.Println(err)
				return err
			}
			fmt.Println("actions", actions)
			actionsInDefinition = append(actionsInDefinition, actions...)
		}
	}
	extendedSymbolMapping[key] = actionsInDefinition
	fmt.Println(extendedSymbolMapping)
	return nil
}
