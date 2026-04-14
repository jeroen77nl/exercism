import gleam/int
import gleam/list
import gleam/result

pub fn is_armstrong_number(number: Int) -> Bool {
  let result = number
  |> int.digits(10)
  |> result.map(sum_of_powers)
  |> result.unwrap(-1)

  number == result
}

fn pow(n: Int, exp: Int) -> Int {
    case exp {
        0 -> 1
        _ -> n * pow(n, exp - 1)
    }
}

fn sum_of_powers(lijst: List(Int)) -> Int {
  list.map(lijst, fn(digit) { pow(digit, list.length(lijst)) })
  |> list.fold(0, int.add)
}