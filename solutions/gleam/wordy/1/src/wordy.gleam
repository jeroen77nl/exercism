import gleam/int
import gleam/list
import gleam/result
import gleam/string

pub type Error {
  SyntaxError
  UnknownOperation
  ImpossibleOperation
}

type Token {
  Number(n: Int)
  Divide
  Multiply
  Plus
  Minus
}

pub fn answer(question: String) -> Result(Int, Error) {
  let begin_test = string.starts_with(question, "What is")
  let end_test = string.ends_with(question, "?")
  case begin_test && end_test {
    False -> Error(UnknownOperation)
    True -> {
      let words =
        question
        |> string.drop_left(string.length("What is"))
        |> string.trim
        |> string.drop_right(string.length("?"))
        |> string.split(" ")
      use tokens <- result.try(tokenize(words, []))
      evaluate(tokens)
    }
  }
}

fn tokenize(
  question: List(String),
  acc: List(Token),
) -> Result(List(Token), Error) {
  case question {
    [] -> Ok(acc |> list.reverse)
    ["divided", "by", ..rest] -> tokenize(rest, [Divide, ..acc])
    ["multiplied", "by", ..rest] -> tokenize(rest, [Multiply, ..acc])
    ["plus", ..rest] -> tokenize(rest, [Plus, ..acc])
    ["minus", ..rest] -> tokenize(rest, [Minus, ..acc])
    ["cubed", ..] -> Error(UnknownOperation)
    [x, ..rest] -> {
      case int.parse(x) {
        Ok(n) -> tokenize(rest, [Number(n), ..acc])
        Error(Nil) -> Error(SyntaxError)
      }
    }
  }
}

fn evaluate(tokens: List(Token)) -> Result(Int, Error) {
  case tokens {
    [Number(_), Divide, Number(0), ..] -> Error(ImpossibleOperation)
    [Number(n), Divide, Number(m), ..rest] -> evaluate([Number(n / m), ..rest])
    [Number(n), Multiply, Number(m), ..rest] ->
      evaluate([Number(n * m), ..rest])
    [Number(n), Plus, Number(m), ..rest] -> evaluate([Number(n + m), ..rest])
    [Number(n), Minus, Number(m), ..rest] -> evaluate([Number(n - m), ..rest])
    [Number(n)] -> Ok(n)
    _ -> Error(SyntaxError)
  }
}
