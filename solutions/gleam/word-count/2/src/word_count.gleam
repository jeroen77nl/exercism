import gleam/dict.{type Dict}
import gleam/list
import gleam/regex
import gleam/string

pub fn count_words(input: String) -> Dict(String, Int) {
  let assert Ok(re) = regex.from_string("[a-z0-9]+('[a-z0-9]+)?")
  input
  |> string.lowercase
  |> regex.scan(re, _)
  |> list.map(fn (result) { result.content })
  |> list.fold(from: dict.new(), with: update_counter)
}

fn update_counter(
  dict: dict.Dict(String, Int),
  word: String,
) -> dict.Dict(String, Int) {
  case
    dict
    |> dict.get(word)
  {
    Ok(count) -> dict.insert(dict, word, count + 1)
    _ -> dict.insert(dict, word, 1)
  }
}
