import gleam/list
import gleam/string

pub fn find_anagrams(word: String, list_of_words: List(String)) {
  list_of_words
  |> list.filter(fn(w) { is_anagram(w, word) })
}

fn is_anagram(s1: String, s2: String) -> Bool {
  let up_s1 = string.uppercase(s1)
  let up_s2 = string.uppercase(s2)
  up_s1 != up_s2 && sort_string(up_s1) == sort_string(up_s2)
}

fn sort_string(s: String) {
  string.join(list.sort(string.split(string.lowercase(s), ""), by: string.compare),"")
}
