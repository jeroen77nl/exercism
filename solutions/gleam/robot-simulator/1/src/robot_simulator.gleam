import gleam/string

pub type Robot {
  Robot(direction: Direction, position: Position)
}

pub type Direction {
  North
  East
  South
  West
}

pub type Position {
  Position(x: Int, y: Int)
}

pub fn create(direction: Direction, position: Position) -> Robot {
  Robot(direction, position)
}

pub fn move(
  direction: Direction,
  position: Position,
  instructions: String,
) -> Robot {
  case string.first(instructions) {
    Ok(inst) if inst == "R" -> move(cw(direction), position, string.drop_left(instructions,1))
    Ok(inst) if inst == "L" -> move(ccw(direction), position, string.drop_left(instructions,1))
    Ok(inst) if inst == "A" -> move(direction, advance(direction, position), string.drop_left(instructions, 1))
    _ -> Robot(direction, position)
  }
}

fn cw(direction: Direction) -> Direction {
    case direction {
        North -> East
        East -> South
        South -> West
        West -> North
    }
}

fn ccw(direction: Direction) -> Direction {
    case direction {
        North -> West
        West -> South
        South -> East
        East -> North
    }
}

fn advance(direction: Direction, position: Position) -> Position {
    case direction {
        North -> Position(position.x, position.y + 1)
        East -> Position(position.x + 1, position.y)
        South -> Position(position.x, position.y - 1)
        West -> Position(position.x - 1, position.y)
    }
}