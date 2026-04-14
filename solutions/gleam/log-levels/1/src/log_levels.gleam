import gleam/string

pub fn message(log_line: String) -> String {
  case log_line {
    "[ERROR]:" <> tekst -> string.trim(tekst)
    "[WARNING]:" <> tekst -> string.trim(tekst)
    "[INFO]:" <> tekst -> string.trim(tekst)
    _ -> string.trim(log_line)
  }
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
  string.concat([message(log_line), " (", log_level(log_line), ")"])
}
