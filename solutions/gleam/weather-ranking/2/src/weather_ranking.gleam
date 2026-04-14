import gleam/float
import gleam/list
import gleam/order.{type Order}

pub type City {
  City(name: String, temperature: Temperature)
}

pub type Temperature {
  Celsius(Float)
  Fahrenheit(Float)
}

pub fn fahrenheit_to_celsius(f: Float) -> Float {
  { f -. 32.0 } *. 5.0 /. 9.0
}

pub fn compare_temperature(left: Temperature, right: Temperature) -> Order {
  float.compare(temperature_to_celcius(left), temperature_to_celcius(right))
}

pub fn sort_cities_by_temperature(cities: List(City)) -> List(City) {
  cities
  |> list.sort(by: fn(c1, c2) {
    compare_temperature(c1.temperature, c2.temperature)
  })
}

fn temperature_to_celcius(t: Temperature) -> Float {
  case t {
    Celsius(t) -> t
    Fahrenheit(t) -> fahrenheit_to_celsius(t)
  }
}
