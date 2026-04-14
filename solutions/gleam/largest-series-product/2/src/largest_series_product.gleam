// Svaught598's solution
// usage of bool.guard
// usage of list.window
// list of try_map

import gleam/bool
import gleam/int
import gleam/list
import gleam/result
import gleam/string

pub fn largest_product(digits: String, span: Int) -> Result(Int, Nil) {
  use <- bool.guard(span > string.length(digits), return: Error(Nil))
  use <- bool.guard(span < 0, return: Error(Nil))
  use <- bool.guard(span == 0, return: Ok(1))

  digits
  |> string.to_graphemes()
  |> list.window(span)
  |> list.try_map(list.try_map(_, int.parse(_)))
  |> result.map(list.map(_, int.product(_)))
  |> result.try(list.reduce(_, int.max))
}
