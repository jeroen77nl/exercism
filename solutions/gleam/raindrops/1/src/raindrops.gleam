import gleam/int

pub fn convert(number: Int) -> String {
  let r3 = convert_iter3(number, "")
  let r5 = convert_iter5(number, r3)
  let r7 = convert_iter7(number, r5)
  case r7 {
    "" -> int.to_string(number)
    _ -> r7
  }
}

fn convert_iter3(number: Int, result: String) -> String {
  case number {
    i if i % 3 == 0 -> result <> "Pling"
    _ -> result
  }
}

fn convert_iter5(number: Int, result: String) -> String {
  case number {
    i if i % 5 == 0 -> result <> "Plang"
    _ -> result
  }
}
fn convert_iter7(number: Int, result: String) -> String {
  case number {
    i if i % 7 == 0 -> result <> "Plong"
    _ -> result
  }
}
