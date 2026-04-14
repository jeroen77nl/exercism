import gleam/list
import gleam/string
import gleam/string_builder

pub fn build(letter: String) -> String {
  let rank = letter_to_rank(letter)
  let result = string_builder.to_string(teken_blok(rank))
  string.drop_right(result, 1)
}

fn teken_blok(rank: Int) -> string_builder.StringBuilder {
  teken_blok_iter(rank, 1, string_builder.new())
}

fn teken_blok_iter(
  rank: Int,
  i: Int,
  result: string_builder.StringBuilder,
) -> string_builder.StringBuilder {
  let length = rank * 2 - 1
  case i {
    i if i > rank -> result
    i if i < rank -> {
      let pos = rank - i + 1
      let derived_letter = rank_to_letter(i)
      let rij =
        string_builder.append(teken_rij(derived_letter, pos, length), "\n")
      let new_result = string_builder.append_builder(result, rij)
      string_builder.append_builder(
        teken_blok_iter(rank, i + 1, new_result),
        rij,
      )
    }
    i if i == rank -> {
      let pos = rank - i + 1
      let derived_letter = rank_to_letter(i)
      let rij =
        string_builder.append(teken_rij(derived_letter, pos, length), "\n")
      string_builder.append_builder(result, rij)
    }
    _ -> string_builder.new()
  }
}

fn teken_rij(
  letter: String,
  pos: Int,
  length: Int,
) -> string_builder.StringBuilder {
  teken_rij_iter(letter: letter, pos: pos, length: length, i: 1, result: string_builder.new())
}

fn teken_rij_iter(
  letter letter: String,
  pos pos: Int,
  length length: Int,
  i i: Int,
  result result: string_builder.StringBuilder,
) -> string_builder.StringBuilder {
  case i {
    i if i > length -> result
    i if i == pos || i == length - pos + 1 ->
      teken_rij_iter(
        letter,
        pos,
        length,
        i + 1,
        string_builder.append(result, letter),
      )
    _ ->
      teken_rij_iter(
        letter,
        pos,
        length,
        i + 1,
        string_builder.append(result, " "),
      )
  }
}

fn letter_to_rank(letter: String) -> Int {
  let int_val_a = case list.first(string.to_utf_codepoints("A")) {
    Ok(cpa) -> string.utf_codepoint_to_int(cpa)
    _ -> -1
  }
  let codepoints = string.to_utf_codepoints(letter)
  let codepoint = list.first(codepoints)
  let int_val = case codepoint {
    Ok(cp) -> string.utf_codepoint_to_int(cp)
    _ -> -1
  }
  int_val - int_val_a + 1
}

fn rank_to_letter(rank: Int) -> String {
  let int_val_a = case list.first(string.to_utf_codepoints("A")) {
    Ok(cp_of_a) -> string.utf_codepoint_to_int(cp_of_a)
    _ -> -1
  }
  let int_val = rank + int_val_a - 1
  let cp = string.utf_codepoint(int_val)
  case cp {
    Ok(cp) -> string.from_utf_codepoints([cp])
    _ -> ""
  }
}
