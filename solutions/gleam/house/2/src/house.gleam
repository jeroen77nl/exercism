import gleam/list
import gleam/string

pub fn recite(start_verse start_verse: Int, end_verse end_verse: Int) -> String {
  list.map(list.range(start_verse, end_verse), fn(i_verse) {
    "This is"
    <> list.fold(list.range(i_verse, 1), "", fn(acc, verse) {
      make_sentence(verse, acc)
    })
  })
  |> string.join("\n")
}

fn make_sentence(verse: Int, acc: String) -> String {
  case verse {
    0 -> acc
    _ -> make_sentence(verse - 1, acc <> " " <> get_text(verse))
  }
}

fn get_text(verse: Int) -> String {
  case verse {
    12 -> "the horse and the hound and the horn that belonged to"
    11 -> "the farmer sowing his corn that kept"
    10 -> "the rooster that crowed in the morn that woke"
    9 -> "the priest all shaven and shorn that married"
    8 -> "the man all tattered and torn that kissed"
    7 -> "the maiden all forlorn that milked"
    6 -> "the cow with the crumpled horn that tossed"
    5 -> "the dog that worried"
    4 -> "the cat that killed"
    3 -> "the rat that ate"
    2 -> "the malt that lay in"
    1 -> "the house that Jack built."
    _ -> ""
  }
}
