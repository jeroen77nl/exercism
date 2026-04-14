import gleam/dict
import gleam/list
import gleam/option
import gleam/string

pub fn is_isogram(phrase phrase: String) -> Bool {
  string.lowercase(phrase)
  |> string.to_graphemes
  |> list.filter(fn(c) { string.contains("abcdefghijklmnopqrstuvwxyz", c) })
  |> list.fold(dict.new(), tel_letter)
  |> dict.values
  |> list.all(fn(g) { g == 1 })
}

fn tel_letter(d: dict.Dict(String, Int), g: String) -> dict.Dict(String, Int) {
  let increment = fn(x) {
    case x {
      option.Some(i) -> i + 1
      option.None -> 1
    }
  }
  dict.upsert(d, g, increment)
}
