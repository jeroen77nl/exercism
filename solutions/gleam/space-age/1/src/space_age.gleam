pub type Planet {
  Mercury
  Venus
  Earth
  Mars
  Jupiter
  Saturn
  Uranus
  Neptune
}

const earth_seconds_in_year: Float = 31557600.0

pub fn age(planet: Planet, seconds: Float) -> Float {
  seconds /. earth_seconds_in_year /. orbital_period(planet)
}

fn orbital_period(planet: Planet) -> Float {
  case planet {
    Mercury -> 0.2408467
    Venus -> 0.61519726
    Earth -> 1.0
    Mars -> 1.8808158
    Jupiter -> 11.862615
    Saturn -> 29.447498
    Uranus -> 84.016846
    Neptune -> 164.79132
  }
}
