pub type Resistance {
  Resistance(unit: String, value: Int)
}

pub fn label(colors: List(String)) -> Result(Resistance, Nil) {
  case colors {
    [c1, c2, c3, ..] -> calculate(c1, c2, c3)
    _ -> Error(Nil)
  }
}

fn calculate(c1: String, c2: String, c3: String) -> Result(Resistance, Nil) {
  case color_value(c1), color_value(c2), color_value(c3) {
    Ok(v1), Ok(v2), Ok(v3) -> {
      let r = {{ v1 * 10 } + v2 } * pow(v3)
      metric_unit(r)
    }
    _, _, _ -> Error(Nil)
  }
}

fn pow(exp: Int) -> Int {
  case exp {
    0 -> 1
    _ -> 10 * pow(exp - 1)
  }
}

fn color_value(color: String) -> Result(Int, Nil) {
  case color {
    "black" -> Ok(0)
    "brown" -> Ok(1)
    "red" -> Ok(2)
    "orange" -> Ok(3)
    "yellow" -> Ok(4)
    "green" -> Ok(5)
    "blue" -> Ok(6)
    "violet" -> Ok(7)
    "grey" -> Ok(8)
    "white" -> Ok(9)
    _ -> Ok(0)
  }
}

fn metric_unit(ohm_value: Int) -> Result(Resistance, Nil) {
  case ohm_value {
    v if v >= 1_000_000_000 -> Ok(Resistance("gigaohms", v / 1_000_000_000))
    v if v >= 1_000_000 -> Ok(Resistance("megaohms", v / 1_000_000))
    v if v >= 1000 -> Ok(Resistance("kiloohms", v / 1000))
    v -> Ok(Resistance("ohms", v))
  }
}
