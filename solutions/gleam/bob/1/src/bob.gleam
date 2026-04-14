import gleam/regex
import gleam/string

pub fn hey(remark: String) -> String {
  let assert Ok(contains_upper) = regex.from_string("[A-Z]")
  let trimmed = string.trim(remark)
  let question = string.ends_with(trimmed, "?")
  let yell =
    string.uppercase(trimmed) == trimmed && regex.check(contains_upper, trimmed)
  let still = string.is_empty(trimmed)
  case question, yell, still {
    True, True, _ -> "Calm down, I know what I'm doing!"
    True, False, _ -> "Sure."
    False, True, False -> "Whoa, chill out!"
    _, _, True -> "Fine. Be that way!"
    _, _, _ -> "Whatever."
  }
}
