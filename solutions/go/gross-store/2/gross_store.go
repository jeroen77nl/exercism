package gross

// Units stores the Gross Store unit measurements.
func Units() map[string]int {
	return map[string]int{
		"quarter_of_a_dozen": 3,
		"half_of_a_dozen":    6,
		"dozen":              12,
		"small_gross":        120,
		"gross":              144,
		"great_gross":        1728,
	}
}

// NewBill creates a new bill.
func NewBill() map[string]int {
	return map[string]int{}
}

// AddItem adds an item to customer bill.
func AddItem(bill, units map[string]int, item, unit string) bool {
	unitValue, exists := units[unit]
	if !exists {
		return false
	}

	billValue, exists := bill[item]
	if exists {
		bill[item] = billValue + unitValue
	} else {
		bill[item] = unitValue
	}
	return true
}

// RemoveItem removes an item from customer bill.
func RemoveItem(bill, units map[string]int, item, unit string) bool {
	/*
Return false if the given item is not in the bill
Return false if the given unit is not in the units map.
Return false if the new quantity would be less than 0.
If the new quantity is 0, completely remove the item from the bill then return true.
Otherwise, reduce the quantity of the item and return true.
	*/
	billValue, exists := bill[item]
	if !exists {
		return false
	}

	unitValue, exists := units[unit]
	if !exists {
		return false
	}

	billValue -= unitValue
	if billValue < 0 {
		return false
	} else if billValue == 0 {
		delete(bill, item)
		return true
	} else {
		bill[item] = billValue
		return true
	}
}

// GetItem returns the quantity of an item that the customer has in his/her bill.
func GetItem(bill map[string]int, item string) (int, bool) {
	billValue, exists := bill[item]
	return billValue, exists
}
