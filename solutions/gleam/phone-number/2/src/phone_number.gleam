import gleam/int
import gleam/list
import gleam/result
import gleam/string

pub fn clean(input: String) -> Result(String, String) {
  let l = string.to_graphemes(input)
  result.try(bevat_letters(l), fn(l) {
    result.try(drop_plus(l), fn(l) {
    //   result.try(bevat_letters(l), fn(l) {
        result.try(bevat_punctuations(l), fn(l) {
          result.try(normalize(l) |> max_11_lang(), fn(l) {
            result.try(lengte_11_begint_met_1(l), fn(l) {
              result.try(min_10_lang(l), fn(l) {
                result.try(check_details(l), fn(l) { Ok(string.join(l, "")) })
              })
            })
          })
        })
    //   })
    })
  })
}

fn bevat_letters(l: List(String)) -> Result(List(String), String) {
  let alfabet = "abcdefghijklmnopqrstuvwxyz"
  case list.any(l, fn(c) { string.contains(alfabet, c) }) {
    True -> Error("letters not permitted")
    False -> Ok(l)
  }
}

fn drop_plus(l: List(String)) -> Result(List(String), String) {
  case l {
    ["+", ..rest] -> Ok(rest)
    _ -> Ok(l)
  }
}

fn max_11_lang(l: List(String)) -> Result(List(String), String) {
  case list.length(l) > 11 {
    True -> Error("must not be greater than 11 digits")
    False -> Ok(l)
  }
}

fn min_10_lang(l: List(String)) -> Result(List(String), String) {
  case list.length(l) < 10 {
    True -> Error("must not be fewer than 10 digits")
    False -> Ok(l)
  }
}

fn check_details(l: List(String)) -> Result(List(String), String) {
  case l {
    ["0", ..] -> Error("area code cannot start with zero")
    ["1", ..] -> Error("area code cannot start with one")
    [_, _, _, "0", ..] -> Error("exchange code cannot start with zero")
    [_, _, _, "1", ..] -> Error("exchange code cannot start with one")
    _ -> Ok(l)
  }
}

fn lengte_11_begint_met_1(l: List(String)) -> Result(List(String), String) {
  case list.length(l), l {
    11, ["1", ..rest] -> Ok(rest)
    11, [_, ..] -> Error("11 digits must start with 1")
    _, _ -> Ok(l)
  }
}

fn bevat_punctuations(l: List(String)) -> Result(List(String), String) {
  let correct_chars = ".()- 0123456789"
  case list.any(l, fn(c) { !string.contains(correct_chars, c) }) {
    True -> Error("punctuations not permitted")
    False -> Ok(l)
  }
}

fn normalize(input: List(String)) -> List(String) {
  input
  |> list.filter(fn(c) {
    case int.parse(c) {
      Ok(_) -> True
      _ -> False
    }
  })
}
