import gleam/int
import gleam/list

pub fn square_of_sum(n: Int) -> Int {
  let lijst = list.range(1, n)
  let som = int.sum(lijst)
  som * som
}

pub fn sum_of_squares(n: Int) -> Int {
  let lijst = list.range(1, n)
  let powered_list = list.map(lijst,  fn(x) { x * x })
  int.sum(powered_list)
}

pub fn difference(n: Int) -> Int {
  square_of_sum(n) - sum_of_squares(n)
}
