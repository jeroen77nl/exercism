import gleam/set
import gleam/string
import gleam/list

const alphabet: String = "abcdefghijklmnopqrstuvwxyz"

pub fn is_pangram(sentence: String) -> Bool {
  sentence
  |> string.lowercase
  |> string.to_graphemes
  |> list.filter(string.contains(alphabet, _))
  |> set.from_list
  |> set.size == 26
}
