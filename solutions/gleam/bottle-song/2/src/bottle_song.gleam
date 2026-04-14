import gleam/string
import gleam/string_builder.{append}

pub fn recite(
  start_bottles start_bottles: Int,
  take_down take_down: Int,
) -> String {
  recite_iter(start_bottles, take_down, "")
  |> string.drop_right(up_to: 2)
}

fn recite_iter(
  start_bottles start_bottles: Int,
  take_down take_down: Int,
  text text: String,
) -> String {
  case take_down {
    0 -> text
    _ -> {
      recite_iter(
        start_bottles: start_bottles - 1,
        take_down: take_down - 1,
        text: text <> verse(start_bottles) <> "\n\n",
      )
    }
  }
}

fn verse(nr_of_bottles: Int) -> String {
  let line_1_2 =
    to_word(nr_of_bottles)
    <> " green "
    <> plural_or_not(nr_of_bottles)
    <> " hanging on the wall,\n"

  string_builder.new()
  |> append(line_1_2)
  |> append(line_1_2)
  |> append("And if one green bottle should accidentally fall,\n")
  |> append(
    "There'll be "
    <> string.lowercase(to_word(nr_of_bottles - 1))
    <> " green "
    <> plural_or_not(nr_of_bottles - 1)
    <> " hanging on the wall.",
  )
  |> string_builder.to_string
}

fn plural_or_not(nr_of_bottles: Int) -> String {
  case nr_of_bottles {
    1 -> "bottle"
    _ -> "bottles"
  }
}

fn to_word(number: Int) -> String {
  case number {
    0 -> "No"
    1 -> "One"
    2 -> "Two"
    3 -> "Three"
    4 -> "Four"
    5 -> "Five"
    6 -> "Six"
    7 -> "Seven"
    8 -> "Eight"
    9 -> "Nine"
    10 -> "Ten"
    _ -> "Unknown"
  }
}
