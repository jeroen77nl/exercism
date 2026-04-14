pub fn reply(guess: Int) -> String {
  case guess {
    i if i < 41 -> "Too low"
    42 -> "Correct"
    41 | 43 -> "So close"
    _ -> "Too high"
  }
}
