import gleam/io
import gleam/list

pub type Error {
  InvalidSquare
}

pub fn square(square: Int) -> Result(Int, Error) {
  case square {
    s if s <= 0 -> Error(InvalidSquare)
    s if s >= 65 -> Error(InvalidSquare)
    _ -> Ok(int_power(2, square - 1))
  }
}

pub fn total() -> Int {
  list.fold(list.range(0,63), 0, fn(acc, i) {acc + int_power(2,i)})
}

fn int_power(x: Int, y: Int) -> Int {
  case y {
    y if y == 0 -> 1
    _ -> {
      let lst = list.range(1, y)
      list.fold(lst, 1, fn(acc, _) { acc * x })
    }
  }
}

pub fn main() {
    int_power(2, 0) |> io.debug
    int_power(2, 1) |> io.debug
    int_power(2, 2) |> io.debug
    int_power(2, 3) |> io.debug
}