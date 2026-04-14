import gleam/list
import gleam/string

pub fn is_paired(value: String) -> Bool {
  string.split(value, "")
  |> list.filter(fn(c) { string.contains("{}[]()", c) })
  |> is_paired_iter([])
}

pub fn is_paired_iter(input: List(String), stack: List(String)) -> Bool {
  case input, stack {
    [], [] -> True
    [], _ -> False
    [head_in, ..rest_in], [] ->
      case closing(head_in) {
        True -> False
        _ -> is_paired_iter(rest_in, [head_in, ..stack])
      }
    [head_in, ..rest_in], [stack_in, ..stack_rest] ->
      case match(head_in, stack_in), closing(head_in) {
        True, _ -> is_paired_iter(rest_in, stack_rest)
        _, True -> False
        _, _ -> is_paired_iter(rest_in, [head_in, ..stack])
      }
  }
}

fn match(closing: String, opening: String) -> Bool {
  list.contains(["{}", "[]", "()"], opening <> closing)
}

fn closing(h: String) -> Bool {
  string.contains("}])", h)
}
