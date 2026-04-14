import gleam/list
import gleam/string

pub fn find_anagrams(word: String, list_of_words: List(String)) {
  list_of_words
  |> list.filter(fn(candidate) {
    string.uppercase(word) != string.uppercase(candidate)
  })
  |> list.filter(fn(w) { sort_string(w) == sort_string(word) })
}

fn sort_string(s: String) -> List(String) {
    s
    |> string.uppercase()
    |> string.split("")
    |> list.sort(by: string.compare)
}
