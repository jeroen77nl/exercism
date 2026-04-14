pub fn egg_count(number: Int) -> Int {
  case number / 2, number % 2 {
    0, 0 -> 0
    0, 1 -> 1
    quotient, 1 -> 1 + egg_count(quotient)
    quotient, _ -> egg_count(quotient)
  }
}
