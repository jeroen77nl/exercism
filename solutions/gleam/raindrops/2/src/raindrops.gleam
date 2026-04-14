import gleam/int

pub fn convert(number: Int) -> String {
  let r3 = convert_iter(number, "", 3, "Pling")
  let r5 = convert_iter(number, r3, 5, "Plang")
  let r7 = convert_iter(number, r5, 7, "Plong")
  case r7 {
    "" -> int.to_string(number)
    _ -> r7
  }
}

fn convert_iter(number: Int, result: String, test_value: Int, test_message: String) -> String {
  case number {
    i if i % test_value == 0 -> result <> test_message
    _ -> result
  }
}
