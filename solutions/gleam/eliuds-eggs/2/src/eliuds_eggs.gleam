pub fn egg_count(number: Int) -> Int {
  case number {
    0 -> 0
    _ -> number % 2 + egg_count(number / 2)
  }
}
