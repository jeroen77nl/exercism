import gleam/dict
import gleam/int
import gleam/list
import gleam/string

pub type Forth {
  Forth(stack: List(Int), words: dict.Dict(Token, List(Token)))
}

pub type ForthError {
  DivisionByZero
  StackUnderflow
  InvalidWord
  UnknownWord
}

pub type Token {
  Number(Int)
  BinaryToken(NumOperator)
  Dup
  Drop
  Swap
  Over
  StartWordDefinition
  EndWordDefinition
  NonReservedWord(String)
}

pub type NumOperator {
  Plus
  Minus
  Multiply
  Divide
}

pub fn new() -> Forth {
  Forth([], dict.new())
}

pub fn format_stack(f: Forth) -> String {
  list.map(f.stack, fn(item) { int.to_string(item) })
  |> list.reverse
  |> string.join(" ")
}

pub fn eval(f: Forth, prog: String) -> Result(Forth, ForthError) {
  let lexemes = string.split(prog, " ")
  let tokens = list.map(lexemes, string_to_token)
  case tokens {
    [StartWordDefinition, ..rest] -> {
      list.take_while(rest, fn(e) { e != EndWordDefinition })
      |> process_definition(f, _)
    }
    _ -> evaluate(f, tokens)
  }
}

fn process_definition(
  f: Forth,
  definition: List(Token),
) -> Result(Forth, ForthError) {
  case definition {
    [] -> Error(InvalidWord)
    [_] -> Error(InvalidWord)
    [name, ..tokens] -> {
      case name {
        Number(_) -> Error(InvalidWord)
        _ ->
          list.map(tokens, fn(token) {
            case dict.get(f.words, token) {
              Ok(new_value) -> new_value
              Error(Nil) -> [token]
            }
          })
          |> list.flatten()
          |> dict.insert(f.words, name, _)
          |> Forth(f.stack, _)
          |> Ok()
      }
    }
  }
}

fn string_to_token(text: String) -> Token {
  let low_text = string.lowercase(text)
  case low_text {
    "+" -> BinaryToken(Plus)
    "-" -> BinaryToken(Minus)
    "*" -> BinaryToken(Multiply)
    "/" -> BinaryToken(Divide)
    "dup" -> Dup
    "drop" -> Drop
    "swap" -> Swap
    "over" -> Over
    ":" -> StartWordDefinition
    ";" -> EndWordDefinition
    _ ->
      case int.parse(low_text) {
        Ok(n) -> Number(n)
        Error(Nil) -> NonReservedWord(low_text)
      }
  }
}

fn evaluate(f: Forth, tokens: List(Token)) -> Result(Forth, ForthError) {
  case tokens {
    [] -> Ok(f)
    [head, ..rest] ->
      case dict.get(f.words, head) {
        Ok(lut) -> evaluate(f, lut |> list.append(rest))
        Error(Nil) -> {
          case process_token(f, head) {
            Error(e) -> Error(e)
            Ok(stack_new) -> evaluate(Forth(stack_new, f.words), rest)
          }
        }
      }
  }
}

fn process_token(f: Forth, token: Token) -> Result(List(Int), ForthError) {
  case token {
    BinaryToken(op) -> {
      case f.stack {
        [] -> Error(StackUnderflow)
        [_] -> Error(StackUnderflow)
        [val2, val1, ..rest] ->
          case op {
            Plus -> Ok([val1 + val2, ..rest])
            Minus -> Ok([val1 - val2, ..rest])
            Multiply -> Ok([val1 * val2, ..rest])
            Divide ->
              case val2 {
                0 -> Error(DivisionByZero)
                _ -> Ok([val1 / val2, ..rest])
              }
          }
      }
    }
    Dup ->
      case f.stack {
        [] -> Error(StackUnderflow)
        [head, ..rest] -> Ok([head, head, ..rest])
      }
    Drop ->
      case f.stack {
        [] -> Error(StackUnderflow)
        [_, ..rest] -> Ok(rest)
      }
    Swap ->
      case f.stack {
        [] -> Error(StackUnderflow)
        [_] -> Error(StackUnderflow)
        [val1, val2, ..rest] -> Ok([val2, val1, ..rest])
      }
    Over ->
      case f.stack {
        [] -> Error(StackUnderflow)
        [_] -> Error(StackUnderflow)
        [val1, val2, ..rest] -> Ok([val2, val1, val2, ..rest])
      }
    Number(n) -> Ok([n, ..f.stack])
    _ -> Error(UnknownWord)
  }
}
