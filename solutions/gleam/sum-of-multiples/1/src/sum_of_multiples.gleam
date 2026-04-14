import gleam/int
import gleam/list

pub fn sum(factors factors: List(Int), limit limit: Int) -> Int {
  list.map(factors, fn(factor) { multiples(factor, limit, 1, []) })
  |> list.flatten
  |> list.sort(by: int.compare)
  |> list.unique
  |> int.sum
}

fn multiples(factor: Int, limit: Int, i: Int, collected: List(Int)) -> List(Int) {
  case i {
    i if i >= limit -> collected
    i if i % factor == 0 -> multiples(factor, limit, i + 1, [i, ..collected])
    _ -> multiples(factor, limit, i + 1, collected)
  }
}
