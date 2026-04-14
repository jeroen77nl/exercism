import gleam/list
import gleam/string

pub fn proteins(rna: String) -> Result(List(String), Nil) {
  rna
  |> string.to_graphemes
  |> to_codons([])
  |> process_codons([])
}

fn to_codons(letters: List(String), acc: List(String)) -> List(String) {
  case letters {
    [p1, p2, p3, ..rest] ->
      to_codons(rest, [string.concat([p1, p2, p3]), ..acc])
    [] -> list.reverse(acc)
    rest -> list.reverse([string.concat(rest), ..acc])
  }
}

fn process_codons(
  codons: List(String),
  acc: List(String),
) -> Result(List(String), Nil) {
  case codons {
    [codin, ..rest] -> {
      case codin_to_protein(codin) {
        Error(Nil) -> Error(Nil)
        Ok("STOP") -> Ok(list.reverse(acc))
        Ok(result) -> process_codons(rest, [result, ..acc])
      }
    }
    [] -> Ok(list.reverse(acc))
  }
}

fn codin_to_protein(codon: String) -> Result(String, Nil) {
  case codon {
    "AUG" -> Ok("Methionine")
    "UUU" | "UUC" -> Ok("Phenylalanine")
    "UUA" | "UUG" -> Ok("Leucine")
    "UCU" | "UCC" | "UCA" | "UCG" -> Ok("Serine")
    "UAU" | "UAC" -> Ok("Tyrosine")
    "UGU" | "UGC" -> Ok("Cysteine")
    "UGG" -> Ok("Tryptophan")
    "UAA" | "UAG" | "UGA" -> Ok("STOP")
    _ -> Error(Nil)
  }
}
