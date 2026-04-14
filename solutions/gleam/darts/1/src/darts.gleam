import gleam/float

pub fn score(x: Float, y: Float) -> Int {
  let assert Ok(d) = distance(x, y)
  case d {
    d if d <=. 1.0 -> 10 
    d if d <=. 5.0 -> 5 
    d if d <=. 10.0 -> 1 
    _ -> 0
  }
}

fn distance(x: Float, y: Float) -> Result(Float, Nil) {
    float.square_root(x *. x +. y *. y)
}
