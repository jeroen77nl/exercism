import gleam/int

pub fn convert(number: Int) -> String {
  let raindrop =
    ""
    |> convert_iter(number, 3, "Pling")
    |> convert_iter(number, 5, "Plang")
    |> convert_iter(number, 7, "Plong")
  case raindrop {
    "" -> int.to_string(number)
    _ -> raindrop
  }
}

fn convert_iter(
  result: String,
  number: Int,
  denominator: Int,
  sound: String,
) -> String {
  case number {
    i if i % denominator == 0 -> result <> sound
    _ -> result
  }
}
