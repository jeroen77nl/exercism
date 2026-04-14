import gleam/dict
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

type Dice {
  Dice(eyes: Int, count: Int)
}

pub fn score(category: Category, dice: List(Int)) -> Int {
  score_of_throw(category, dice_to_throw(dice))
}

fn score_of_throw(category: Category, throw: List(Dice)) -> Int {
  case category {
    Ones | Twos | Threes | Fours | Fives | Sixes ->
      score_count_number(category, throw)
    FullHouse -> full_house(throw)
    FourOfAKind -> four_of_a_kind(throw)
    LittleStraight -> little_straight(throw)
    BigStraight -> big_straight(throw)
    Choice -> choice(throw)
    Yacht -> yacht(throw)
  }
}

fn dice_to_throw(dice: List(Int)) -> List(Dice) {
  dice
  |> list.group(by: fn(i) { i })
  |> dict.to_list
  |> list.map(fn(e: #(Int, List(Int))) { Dice(e.0, list.length(e.1)) })
}

fn score_count_number(category: Category, throw: List(Dice)) -> Int {
  let teller =
    throw
    |> list.find(fn(dice) { dice.eyes == category_to_number(category) })
  case teller {
    Ok(Dice(eyes, count)) -> eyes * count
    Error(Nil) -> 0
  }
}

fn full_house(throw: List(Dice)) -> Int {
  case throw {
    [a, b] ->
      case a.count, b.count {
        2, 3 | 3, 2 -> a.eyes * a.count + b.eyes * b.count
        _, _ -> 0
      }
    _ -> 0
  }
}

fn four_of_a_kind(throw: List(Dice)) -> Int {
  case throw {
    [a, b] ->
      case a.count, b.count {
        1, 4 -> b.eyes * b.count
        4, 1 -> a.eyes * a.count
        _, _ -> 0
      }
    // a yacht with 5 equal eyes
    [a] -> a.eyes * 4
    _ -> 0
  }
}

fn little_straight(throw: List(Dice)) -> Int {
  case throw {
    [a, b, c, d, e] ->
      case a.eyes, b.eyes, c.eyes, d.eyes, e.eyes {
        1, 2, 3, 4, 5 -> 30
        _, _, _, _, _ -> 0
      }
    _ -> 0
  }
}

fn big_straight(throw: List(Dice)) -> Int {
  case throw {
    [a, b, c, d, e] ->
      case a.eyes, b.eyes, c.eyes, d.eyes, e.eyes {
        2, 3, 4, 5, 6 -> 30
        _, _, _, _, _ -> 0
      }
    _ -> 0
  }
}

fn choice(throw: List(Dice)) -> Int {
  throw
  |> list.fold(0, fn(acc, elem) { acc + elem.eyes * elem.count })
}

fn yacht(throw: List(Dice)) -> Int {
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
