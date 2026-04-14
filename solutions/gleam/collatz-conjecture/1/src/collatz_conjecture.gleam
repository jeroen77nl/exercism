pub type Error {
  NonPositiveNumber
}

pub fn steps(number: Int) -> Result(Int, Error) {
  case number {
    n if n <= 0 -> Error(NonPositiveNumber)
    _ -> Ok(steps_iter(number, 0))
  }
}

fn steps_iter(number: Int, step_count: Int) -> Int {
  case number {
    1 -> step_count
    n if n % 2 == 0 -> steps_iter(number / 2, step_count + 1)
    _ -> steps_iter(number * 3 + 1, step_count + 1)
  }
}
