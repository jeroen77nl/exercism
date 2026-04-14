import gleam/dict
import gleam/io
import gleam/list

pub type Category {
  Ones
  Twos
  Threes
  Fours
  Fives
  Sixes
  FullHouse
  FourOfAKind
  LittleStraight
  BigStraight
  Choice
  Yacht
}

pub fn score(category: Category, dice: List(Int)) -> Int {
  let throw = dice_to_throw(dice)
  case category {
    Ones | Twos | Threes | Fours | Fives | Sixes ->
      score_count_number(category, throw)
    FullHouse -> full_house(throw)
    FourOfAKind -> four_of_a_kind(throw)
    LittleStraight -> little_straight(throw)
    BigStraight -> big_straight(throw)
    Choice -> choice(throw)
    Yacht -> yacht(throw)
    _ -> 0
  }
}

fn dice_to_throw(dice: List(Int)) -> List(#(Int, Int)) {
  dice
  |> list.group(by: fn(i) { i })
  |> dict.to_list
  |> list.map(with: fn(elem: #(Int, List(Int))) {
    #(elem.0, list.length(elem.1))
  })
}

fn score_count_number(category: Category, throw: List(#(Int, Int))) -> Int {
  let teller =
    throw
    |> list.find(fn(elem: #(Int, Int)) {
      elem.0 == category_to_number(category)
    })
  case teller {
    Ok(#(number, count)) -> number * count
    Error(Nil) -> 0
  }
}

fn full_house(throw: List(#(Int, Int))) -> Int {
  case throw {
    [a, b] ->
      case a.1, b.1 {
        2, 3 | 3, 2 -> a.0 * a.1 + b.0 * b.1
        _, _ -> 0
      }
    _ -> 0
  }
}

fn four_of_a_kind(throw: List(#(Int, Int))) -> Int {
  case throw {
    [a, b] ->
      case a.1, b.1 {
        1, 4 -> b.0 * b.1
        4, 1 -> a.0 * a.1
        _, _ -> 0
      }
    [a] -> a.0 * 4
    _ -> 0
  }
}

fn little_straight(throw: List(#(Int, Int))) -> Int {
  case throw {
    [a, b, c, d, e] ->
      case a.0, b.0, c.0, d.0, e.0 {
        1, 2, 3, 4, 5 -> 30
        _, _, _, _, _ -> 0
      }
    _ -> 0
  }
}

fn big_straight(throw: List(#(Int, Int))) -> Int {
  case throw {
    [a, b, c, d, e] ->
      case a.0, b.0, c.0, d.0, e.0 {
        2, 3, 4, 5, 6 -> 30
        _, _, _, _, _ -> 0
      }
    _ -> 0
  }
}

fn choice(throw: List(#(Int, Int))) -> Int {
  throw
  |> list.fold(0, fn(acc, elem) { acc + elem.0 * elem.1 })
}

fn yacht(throw: List(#(Int, Int))) -> Int {
  case throw |> list.length {
    1 -> 50
    _ -> 0
  }
}

fn category_to_number(category: Category) -> Int {
  case category {
    Ones -> 1
    Twos -> 2
    Threes -> 3
    Fours -> 4
    Fives -> 5
    Sixes -> 6
    _ -> -1
  }
}

pub fn main() {
  score(Ones, [1, 1, 1, 2, 2])
  |> io.debug
  score(FullHouse, [1, 1, 1, 2, 2])
  |> io.debug
  score(FourOfAKind, [1, 1, 1, 1, 2])
  |> io.debug
  score(FourOfAKind, [2, 3, 3, 3, 3])
  |> io.debug
  score(LittleStraight, [1, 2, 3, 4, 5])
  |> io.debug
  score(LittleStraight, [2, 3, 4, 5, 6])
  |> io.debug
  score(BigStraight, [1, 2, 3, 4, 5])
  |> io.debug
  score(BigStraight, [2, 3, 4, 5, 6])
  |> io.debug
  score(Choice, [2, 3, 4, 5, 6])
  |> io.debug
  score(Yacht, [2, 2, 2, 2, 2])
  |> io.debug
}
