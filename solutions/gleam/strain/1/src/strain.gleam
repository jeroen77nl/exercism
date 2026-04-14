import gleam/list

pub fn keep(list: List(t), predicate: fn(t) -> Bool) -> List(t) {
  keep_iter(list, predicate, [])
}

fn keep_iter(
  list: List(t),
  predicate: fn(t) -> Bool,
  new_list: List(t),
) -> List(t) {
  case list {
    [] -> list.reverse(new_list)
    [head, ..rest] -> {
      case predicate(head) {
        True -> keep_iter(rest, predicate, list.prepend(new_list, head))
        False -> keep_iter(rest, predicate, new_list)
      }
    }
  }
}

pub fn discard(list: List(t), predicate: fn(t) -> Bool) -> List(t) {
  keep(list, fn(e) { !predicate(e) })
}
