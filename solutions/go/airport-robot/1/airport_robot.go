package airportrobot

type Greeter interface {
	LanguageName() string
	Greet(name string) string
}

func SayHello(name string, greeter Greeter) string {
	return "I can speak " + greeter.LanguageName() + ": " + greeter.Greet(name)
}

/*
2. Implement Italian
Now your job is to make the robot work for people that scan Italian passports.
For that, create a struct Italian and implement the two methods that are needed for the struct 
to fulfill the Greeter interface you set up in task 1. You can greet someone in Italian with "Ciao {name}!".
*/

type Italian struct {
}

func (Italian) LanguageName() string {
	return "Italian"
}

func (Italian) Greet(name string) string {
	return "Ciao " + name + "!"
}

type Portuguese struct {
}

func (Portuguese) LanguageName() string {
	return "Portuguese"
}

func (Portuguese) Greet(name string) string {
	return "Olá " + name + "!"
}