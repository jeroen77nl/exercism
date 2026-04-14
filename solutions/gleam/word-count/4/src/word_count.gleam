import gleam/dict.{type Dict}
import gleam/list
import gleam/regex
import gleam/string
import gleam/option.{Some, None}

pub fn count_words(input: String) -> Dict(String, Int) {
  let assert Ok(re) = regex.from_string("[a-z0-9]+('[a-z0-9]+)?")
  input
  |> string.lowercase
  |> regex.scan(re, _)
  |> list.map(fn(result) { result.content })
  |> list.fold(from: dict.new(), with: fn(d,w) { dict.upsert(d, w, increment)})
}

fn increment(n: option.Option(Int)) -> Int{
  case n {
    Some(i) -> i + 1
    None -> 1
  }
}
