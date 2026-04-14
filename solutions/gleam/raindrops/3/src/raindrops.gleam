import gleam/int

pub fn convert(number: Int) -> String {

    let raindrop = ""
    |> convert_iter(number, 3, "Pling")
    |> convert_iter(number, 5, "Plang")
    |> convert_iter(number, 7, "Plong")
  case raindrop {
    "" -> int.to_string(number)
    _ -> raindrop
  }
}

fn convert_iter(result: String, number: Int, test_value: Int, test_message: String) -> String {
  case number {
    i if i % test_value == 0 -> result <> test_message
    _ -> result
  }
}
