import gleam/string

pub fn hey(remark: String) -> String {
  let trimmed = string.trim(remark)

  let question = string.ends_with(trimmed, "?")
  let yell =
    string.uppercase(remark) == remark && string.lowercase(remark) != remark
  let still = string.is_empty(trimmed)

  case question, yell, still {
    _   , _   , True -> "Fine. Be that way!"
    True, True, _    -> "Calm down, I know what I'm doing!"
    True, _   , _    -> "Sure."
    _   , True, _    -> "Whoa, chill out!"
    _   , _   , _    -> "Whatever."
  }
}
