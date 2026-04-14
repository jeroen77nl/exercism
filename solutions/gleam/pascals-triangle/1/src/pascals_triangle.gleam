import gleam/io
import gleam/list

pub fn rows(n: Int) -> List(List(Int)) {
  list.reverse(rows_iter(n, 1, [[1]]))
}

fn rows_iter(n: Int, i: Int, l: List(List(Int))) -> List(List(Int)) {
  case n {
    0 -> []
    _ if i == n -> l
    _ ->
      case l {
        [head, ..] -> rows_iter(n, i + 1, [make_row(head, []), ..l])
        _ -> rows_iter(n, i + 1, [make_row([], []), ..l])
      }
  }
}

fn make_row(l_in: List(Int), l_uit: List(Int)) -> List(Int) {
  case l_in {
    [] -> l_uit |> list.append([1])
    [i, j, ..rest] -> make_row([j, ..rest], [i + j, ..l_uit])
    [j, ..] -> make_row([], [j, ..l_uit])
  }
}

pub fn main() {
  make_row([], [])
  |> io.debug
  make_row([1], [])
  |> io.debug
  make_row([1, 1], [])
  |> io.debug
  make_row([1, 2, 1], [])
  |> io.debug
  rows(0)
  |> io.debug
  rows(1)
  |> io.debug
  rows(2)
  |> io.debug
  rows(3)
  |> io.debug
}
