import gleam/int
import gleam/list
import gleam/float

pub type Classification {
  Perfect
  Abundant
  Deficient
}

pub type Error {
  NonPositiveInt
}

pub fn classify(number: Int) -> Result(Classification, Error) {
  case number <= 0 {
    True -> Error(NonPositiveInt)
    False -> {
        case sum_of_factors(number) {
        s if s == number -> Ok(Perfect)
        s if s > number -> Ok(Abundant)
        _ -> Ok(Deficient)
      }
    }
  }
}

fn sum_of_factors(number: Int) -> Int {
  number
  |> factors
  |> int.sum()
}

fn factors(number: Int) -> List(Int) {
  case number {
    1 -> []
    _ ->
      list.fold(list.range(1, number - 1), [], fn(acc, i) {
        case number % i {
          0 -> [i, ..acc]
          _ -> acc
        }
      })
  }
}

pub fn main() {
  classify(2)
}
