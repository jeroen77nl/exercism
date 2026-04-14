import gleam/float
import gleam/order.{type Order}
import gleam/list

pub type City {
  City(name: String, temperature: Temperature)
}

pub type Temperature {
  Celsius(Float)
  Fahrenheit(Float)
}

pub fn fahrenheit_to_celsius(f: Float) -> Float {
  {f -. 32.0} *. 5.0 /. 9.0
}

pub fn compare_temperature(left: Temperature, right: Temperature) -> Order {
  let left_c = case left {
    Fahrenheit(f) -> fahrenheit_to_celsius(f)
    Celsius(c) -> c
  }
  let right_c = case right {
    Fahrenheit(f) -> fahrenheit_to_celsius(f)
    Celsius(c) -> c
  }
  float.compare(left_c, right_c)
}

pub fn sort_cities_by_temperature(cities: List(City)) -> List(City) {
  cities
  |> list.sort(by: fn(c1, c2) {compare_temperature(c1.temperature, c2.temperature)})
}
