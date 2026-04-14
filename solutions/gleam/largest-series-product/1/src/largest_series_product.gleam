import gleam/int
import gleam/list
import gleam/result
import gleam/string

pub fn largest_product(digits: String, span: Int) -> Result(Int, Nil) {
  use _ <- result.try(check_args(digits, span))
  case digits, span {
    "", 0 -> Ok(1)
    _, _ -> process(digits, span)
  }
}

fn check_args(digits: String, span: Int) -> Result(Nil, Nil) {
  case span >= 0 && string.length(digits) >= span {
    True -> Ok(Nil)
    False -> Error(Nil)
  }
}

fn process(digits: String, span: Int) -> Result(Int, Nil) {
  use int_digits <- result.try(
    digits
    |> string.to_graphemes
    |> make_sublists(span, [])
    |> strings_to_ints,
  )
  list.fold(int_digits, -1, fn(max_product, sublist) {
    let product_of_sublist =
      list.fold(sublist, 1, fn(product, n) { product * n })
    int.max(product_of_sublist, max_product)
  })
  |> Ok()
}

fn strings_to_ints(sublists: List(List(String))) -> Result(List(List(Int)), Nil) {
  sublists
  |> list.map(sublist_to_ints)
  |> result.all
}

fn sublist_to_ints(sublist: List(String)) -> Result(List(Int), Nil) {
  sublist
  |> list.map(int.parse)
  |> result.all
}

fn make_sublists(
  digits: List(String),
  span: Int,
  acc: List(List(String)),
) -> List(List(String)) {
  case digits {
    [] -> acc
    [_, ..rest] -> {
      case list.length(digits) < span {
        True -> make_sublists(rest, span, acc)
        _ -> {
          let substring =
            digits
            |> list.take(span)
          make_sublists(rest, span, [substring, ..acc])
        }
      }
    }
  }
}
