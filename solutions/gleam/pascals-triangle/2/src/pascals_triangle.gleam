import gleam/list
import gleam/result

pub fn rows(n: Int) -> List(List(Int)) {
  list.reverse(rows_iter(n, []))
}

fn rows_iter(n: Int, l: List(List(Int))) -> List(List(Int)) {
  case n {
    0 -> l
    _ ->
      list.first(l)
      |> result.unwrap([])
      |> make_row([])
      |> list.prepend(l, _)
      |> rows_iter(n - 1, _)
  }
}

fn make_row(l_in: List(Int), l_uit: List(Int)) -> List(Int) {
  case l_in {
    [i, j, ..rest] -> make_row([j, ..rest], [i + j, ..l_uit])
    [j, ..] -> make_row([], [j, ..l_uit])
    [] -> l_uit |> list.append([1])
  }
}
