import gleam/string 

pub fn distance(strand1: String, strand2: String) -> Result(Int, Nil) {
  distance_iter(string.to_graphemes(strand1), string.to_graphemes(strand2), 0)
}

fn distance_iter(s1: List(String), s2: List(String), distance: Int) -> Result(Int, Nil) {
  case s1, s2 {
    [], [] -> Ok(distance)
    [], _ -> Error(Nil)
    _, [] -> Error(Nil)
    [l1, ..rest1], [l2, ..rest2] -> case l1 == l2 {
      True -> distance_iter(rest1, rest2, distance)
      False -> distance_iter(rest1, rest2, distance + 1)
    }
  }
}
