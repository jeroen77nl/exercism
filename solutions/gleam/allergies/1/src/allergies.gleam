import gleam/list

pub type Allergen {
  Eggs
  Peanuts
  Shellfish
  Strawberries
  Tomatoes
  Chocolate
  Pollen
  Cats
}

pub fn allergic_to(allergen: Allergen, score: Int) -> Bool {
  allergen_to_int(allergen)
  |> allergic_to_iter(score, 1, _)
}

pub fn allergic_to_iter(score: Int, factor: Int, factor_needed: Int) -> Bool {
  case score {
    0 -> False
    _ -> {
      case factor == factor_needed && score % 2 == 1 {
        True -> True
        _ -> allergic_to_iter(score / 2, factor * 2, factor_needed)
      }
    }
  }
}

pub fn list(score: Int) -> List(Allergen) {
  [Cats, Pollen, Chocolate, Tomatoes, Strawberries, Shellfish, Peanuts, Eggs]
  |> list.fold([], fn(acc, allergen) {
    case allergic_to(allergen, score % 256) {
      True -> [allergen, ..acc]
      False -> acc
    }
  })
}

pub fn list_iter(
  factor_allergens: List(#(Int, Allergen)),
  score: Int,
  acc: List(Allergen),
) -> List(Allergen) {
  case factor_allergens {
    [] -> list.reverse(acc)
    [head, ..rest] -> {
      case head.0, head.1 {
        factor, _ if score <= factor -> list_iter(rest, score, acc)
        factor, allergen -> {
          let quot = score / factor
          let rem = score % factor
          let new_score = { quot * factor } - rem
          list_iter(rest, new_score, [allergen, ..acc])
        }
      }
    }
  }
}

fn allergen_to_int(allergen: Allergen) -> Int {
  case allergen {
    Eggs -> 1
    Peanuts -> 2
    Shellfish -> 4
    Strawberries -> 8
    Tomatoes -> 16
    Chocolate -> 32
    Pollen -> 64
    Cats -> 128
  }
}
