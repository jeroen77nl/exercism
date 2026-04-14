import gleam/list
import gleam/result
import gleam/string

pub fn slices(input: String, size: Int) -> Result(List(String), Error) {
  checks(input, size)
  |> result.try(fn(in) { Ok(process(in, size, [])) })
}

pub type Error {
  SliceLengthTooLarge
  SliceLengthZero
  SliceLengthNegative
  EmptySeries
}

fn checks(input: String, size: Int) -> Result(String, Error) {
  case string.length(input), size {
    0, _ -> Error(EmptySeries)
    _, s if s == 0 -> Error(SliceLengthZero)
    _, s if s < 0 -> Error(SliceLengthNegative)
    l, s if l < s -> Error(SliceLengthTooLarge)
    _, _ -> Ok(input)
  }
}

fn process(input: String, size: Int, acc: List(String)) -> List(String) {
  case string.length(input) < size {
    True -> list.reverse(acc)
    False -> {
      let substr = string.slice(input, 0, size)
      let rest = string.slice(input, 1, string.length(input) - 1)
      process(rest, size, [substr, ..acc])
    }
  }
}
