import gleam/io
import gleam/list
import gleam/result

pub type Nucleotide {
  Adenine
  Cytosine
  Guanine
  Thymine
}

pub fn encode_nucleotide(nucleotide: Nucleotide) -> Int {
  case nucleotide {
    Adenine -> 0
    Cytosine -> 1
    Guanine -> 2
    Thymine -> 3
  }
}

pub fn decode_nucleotide(nucleotide: Int) -> Result(Nucleotide, Nil) {
  case nucleotide {
    0 -> Ok(Adenine)
    1 -> Ok(Cytosine)
    2 -> Ok(Guanine)
    3 -> Ok(Thymine)
    _ -> Error(Nil)
  }
}

pub fn encode(dna: List(Nucleotide)) -> BitArray {
  dna
  |> list.fold(<<>>, fn(acc, nuc) { <<acc:bits, encode_nucleotide(nuc):2>> })
}

pub fn decode(dna: BitArray) -> Result(List(Nucleotide), Nil) {
    use res <- result.try(decode_iter(dna, []))
    list.reverse(res)
    |> Ok
}

fn decode_iter(
  dna: BitArray,
  acc: List(Nucleotide),
) -> Result(List(Nucleotide), Nil) {
  case dna {
    <<>> -> Ok(acc)
    <<_:1>> -> Error(Nil)
    _ -> {
      let assert <<value:2, rest:bits>> = dna
      use dec <- result.try(decode_nucleotide(value))
      let acc = list.prepend(acc, dec)
      decode_iter(rest, acc)
    }
  }
}
