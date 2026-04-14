import gleam/int
import gleam/io
import gleam/list
import gleam/set

pub fn main() {
  lowest_price([1, 1, 2, 2, 3, 3, 4, 5, 1, 1, 2, 2, 3, 3, 4, 5])
}

pub fn lowest_price(books: List(Int)) -> Float {
  books
  |> list.sort(by: int.compare)
  |> optimale_combis
  |> corrigeer_combis_345
  |> list.map(fn(x) { group_price(x) })
  |> list.fold(from: 0.0, with: fn(x, y) { x +. y })
}

fn optimale_combis(books: List(Int)) -> List(Int) {
  optimale_combis_iter(books, [])
}

fn optimale_combis_iter(books: List(Int), combis: List(Int)) -> List(Int) {
  case books {
    [] -> combis
    _ -> {
      let group = books |> set.from_list
      let group_size = set.size(group)
      let group_as_list = group |> set.to_list |> list.sort(by: int.compare)
      optimale_combis_iter(diff(books, group_as_list), [group_size, ..combis])
    }
  }
}

fn corrigeer_combis_345(books: List(Int)) -> List(Int) {
  case list.contains(books, 3) && list.contains(books, 5) {
    False -> books
    True -> {
      books
      |> remove(3)
      |> remove(5)
      |> list.prepend(4)
      |> list.prepend(4)
      |> corrigeer_combis_345
    }
  }
}

fn remove(books: List(Int), e: Int) -> List(Int) {
  case books {
    [] -> books
    [head, ..rest] if head == e -> rest
    [head, ..rest] -> [head, ..remove(rest, e)]
  }
}

fn group_price(group_size: Int) -> Float {
  case group_size {
    1 -> 800.0
    2 -> 2.0 *. group_price(1) *. { 1.0 -. 0.05 }
    3 -> 3.0 *. group_price(1) *. { 1.0 -. 0.1 }
    4 -> 4.0 *. group_price(1) *. { 1.0 -. 0.2 }
    5 -> 5.0 *. group_price(1) *. { 1.0 -. 0.25 }
    _ -> 0.0
  }
}

fn diff(l1: List(Int), l2: List(Int)) -> List(Int) {
  // remove element in l2 from l1 (both are sorted)
  case l1, l2 {
    xs, [] -> xs
    [x, ..xs], [y, ..ys] if x == y -> diff(xs, ys)
    [x, ..xs], [y, ..] if x < y -> [x, ..diff(xs, l2)]
    _, _ -> {
      io.debug("diff, l1 mag niet leeg zijn als l2 gevuld is")
      []
    }
  }
}
