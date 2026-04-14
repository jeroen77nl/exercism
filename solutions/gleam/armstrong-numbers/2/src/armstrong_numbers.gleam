import gleam/int
import gleam/list

pub fn is_armstrong_number(number: Int) -> Bool {
  let assert Ok(list_of_digits) = int.digits(number, 10)
  let nr_of_digits = list.length(list_of_digits)
  let result =
    list_of_digits
    |> list.map(pow(_, nr_of_digits))
    |> list.fold(0, int.add)

  number == result
}

fn pow(n: Int, exp: Int) -> Int {
  case exp {
    0 -> 1
    _ -> n * pow(n, exp - 1)
  }
}
