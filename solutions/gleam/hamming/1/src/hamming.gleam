import gleam/string 

pub fn distance(strand1: String, strand2: String) -> Result(Int, Nil) {
  case string.length(strand1) == string.length(strand2) {
    False -> Error(Nil)
    True -> Ok(distance_iter(strand1, strand2, 0))
  }
}

fn distance_iter(s1: String, s2: String, distance: Int) -> Int {
  case string.is_empty(s1) {
    True -> distance
    False -> case string.first(s1) == string.first(s2) {
      True -> distance_iter(string.drop_start(s1, 1), string.drop_start(s2,1), distance)
      False -> distance_iter(string.drop_start(s1, 1), string.drop_start(s2,1), distance + 1)
    }
  }
}
