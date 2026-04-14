// Package census simulates a system used to collect census data.
package census

// Resident represents a resident in this city.
type Resident struct {
	Name    string
	Age     int
	Address map[string]string
}

func NewResident(name string, age int, address map[string]string) *Resident {
	return &Resident{
		Name: name,
		Age: age,
		Address: address,
	}
}

func (r *Resident) HasRequiredInfo() bool {	
	return r.Address["street"] != "" && r.Name != ""
}

func (r *Resident) Delete() {
	r.Age = 0
	r.Name = ""
	r.Address = nil
}

// Count counts all residents that have provided the required information.
func Count(residents []*Resident) int {
	count := 0
	for _, resident := range residents {
		if resident.HasRequiredInfo() {
			count++
		}
	}
	return count
}
