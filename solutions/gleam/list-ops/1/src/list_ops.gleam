pub fn append(first first: List(a), second second: List(a)) -> List(a) {
  case first {
    [] -> second
    [head, ..rest] -> [head, ..append(rest, second)]
  }
}

pub fn concat(lists: List(List(a))) -> List(a) {
  case lists {
    [] -> []
    [head, ..rest] -> append(head, concat(rest))
  }
}

pub fn filter(list: List(a), function: fn(a) -> Bool) -> List(a) {
  case list {
    [] -> []
    [head, ..rest] ->
      case function(head) {
        True -> append([head], filter(rest, function))
        False -> filter(rest, function)
      }
  }
}

pub fn length(list: List(a)) -> Int {
  case list {
    [] -> 0
    [_, ..rest] -> 1 + length(rest)
  }
}

pub fn map(list: List(a), function: fn(a) -> b) -> List(b) {
  case list {
    [] -> []
    [head, ..rest] -> [function(head), ..map(rest, function)]
  }
}

pub fn foldl(
  over list: List(a),
  from initial: b,
  with function: fn(b, a) -> b,
) -> b {
  case list {
    [] -> initial
    [head, ..rest] -> {
      let result = function(initial, head)
      foldl(over: rest, from: result, with: function)
    }
  }
}

pub fn foldr(
  over list: List(a),
  from initial: b,
  with function: fn(b, a) -> b,
) -> b {
  case list {
    [] -> initial
    [head, ..rest] -> function(foldr(rest, initial, function), head)
  }
}

pub fn reverse(list: List(a)) -> List(a) {
  case list {
    [] -> []
    [head, ..rest] -> append(reverse(rest) , [head])
  }
}
