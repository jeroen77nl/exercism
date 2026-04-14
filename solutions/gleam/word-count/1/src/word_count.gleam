import gleam/dict.{type Dict}
import gleam/list
import gleam/regex
import gleam/string

pub fn count_words(input: String) -> Dict(String, Int) {
  let assert Ok(re) = regex.from_string("[^a-zA-Z0-9']")
  regex.split(with: re, content: string.lowercase(input))
  |> list.map(fn(s) { trim_quotes(s) })
  |> list.filter(fn(s) { s != "" })
  |> list.fold(from: dict.new(), with: update_counter)
}

fn trim_quotes(s: String) {
    let f = case string.first(s) {
        Ok("'") -> string.drop_left(s, 1)
        _ -> s
    }
    case string.last(f) {
        Ok("'") -> string.drop_right(f, 1)
        _ -> f
    }
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
