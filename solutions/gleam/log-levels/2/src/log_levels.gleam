import gleam/string

pub fn message(log_line: String) -> String {
  case log_line {
    "[ERROR]:" <> tekst -> tekst
    "[WARNING]:" <> tekst -> tekst
    "[INFO]:" <> tekst -> tekst
    _ -> string.trim(log_line)
  }
  |> string.trim
}

pub fn log_level(log_line: String) -> String {
  case log_line {
    "[ERROR]" <> _ -> "error"
    "[WARNING]" <> _ -> "warning"
    "[INFO]" <> _ -> "info"
    _ -> log_line
  }
}

pub fn reformat(log_line: String) -> String {
  message(log_line) <> " (" <> log_level(log_line) <> ")"
}
