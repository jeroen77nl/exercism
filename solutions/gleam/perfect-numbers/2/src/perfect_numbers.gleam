import gleam/float
import gleam/int
import gleam/io
import gleam/list

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
    _ -> {
      let assert Ok(root) = int.square_root(number)
      let limit = float.truncate(root)
      list.fold(list.range(1, limit), [], fn(acc, i) {
        case number % i {
          0 if i > 1 -> [i, number / i, ..acc]
          0 -> [i, ..acc]
          _ -> acc
        }
      })
     |> list.unique
    }
  }
}

pub fn main() {
  classify(2)
}
