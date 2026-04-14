import gleam/string
import gleam/string_builder

pub fn recite(
  start_bottles start_bottles: Int,
  take_down take_down: Int,
) -> String {
  let result = recite_iter(start_bottles, take_down, "")
  string.drop_right(from: result, up_to: 2)
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
        start_bottles - 1,
        take_down - 1,
        text <> verse(start_bottles) <> "\n\n",
      )
    }
  }
}

fn verse(nr_of_bottles: Int) -> String {
  let nr = int_to_str(nr_of_bottles)
  let decr_nr = string.lowercase(int_to_str(nr_of_bottles - 1))
  let bottles_text = case nr_of_bottles {
    1 -> "bottle"
    _ -> "bottles"
  }
  let bottles_text_decr = case nr_of_bottles - 1 {
    1 -> "bottle"
    _ -> "bottles"
  }
  string_builder.new()
  |> string_builder.append(
    nr <> " green " <> bottles_text <> " hanging on the wall,\n",
  )
  |> string_builder.append(
    nr <> " green " <> bottles_text <> " hanging on the wall,\n",
  )
  |> string_builder.append(
    "And if one green bottle should accidentally fall,\n",
  )
  |> string_builder.append(
    "There'll be "
    <> decr_nr
    <> " green "
    <> bottles_text_decr
    <> " hanging on the wall.",
  )
  |> string_builder.to_string
}

fn int_to_str(number: Int) -> String {
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
