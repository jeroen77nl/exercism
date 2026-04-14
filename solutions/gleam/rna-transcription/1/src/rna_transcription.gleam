import gleam/list
import gleam/string

pub fn to_rna(dna: String) -> Result(String, Nil) {
  let rna =
    string.to_graphemes(dna)
    |> list.map(transcribe)
    |> string.concat()

  case string.length(rna) == string.length(dna) {
    True -> Ok(rna)
    False -> Error(Nil)
  }
}

fn transcribe(dna_letter: String) -> String {
  case dna_letter {
    "G" -> "C"
    "C" -> "G"
    "T" -> "A"
    "A" -> "U"
    _ -> ""
  }
}
