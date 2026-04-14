import gleam/list

pub fn today(days: List(Int)) -> Int {
  case days {
    [] -> 0
    [fst, ..] -> fst
  }
}

pub fn increment_day_count(days: List(Int)) -> List(Int) {
  case days {
    [] -> [1]
    [_, ..rest] -> [today(days) + 1, ..rest]
  }
}

pub fn has_day_without_birds(days: List(Int)) -> Bool {
  case days {
    [] -> False
    _ -> list.any(days, fn(i) {i == 0})
  }
}

pub fn total(days: List(Int)) -> Int {
  case days {
    [] -> 0
    [i, .. rest] -> i + total(rest)
  }
}

pub fn busy_days(days: List(Int)) -> Int {
  days
  |> list.filter(fn(i) { i >= 5})
  |> list.length
}
