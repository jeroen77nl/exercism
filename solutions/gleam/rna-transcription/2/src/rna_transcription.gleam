import gleam/list
import gleam/string
import gleam/result

pub fn to_rna(dna: String) -> Result(String, Nil) {
    string.to_graphemes(dna)
    |> list.try_map(transcribe)
    |> result.map(string.concat)
}

fn transcribe(dna_letter: String) -> Result(String, Nil) {
  case dna_letter {
    "G" -> Ok("C")
    "C" -> Ok("G")
    "T" -> Ok("A")
    "A" -> Ok("U")
    _ -> Error(Nil)
  }
}
