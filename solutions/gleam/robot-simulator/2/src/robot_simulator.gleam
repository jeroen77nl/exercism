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
  case instructions {
    "R" <> rest -> move(cw(direction), position, rest)
    "L" <> rest -> move(ccw(direction), position, rest)
    "A" <> rest -> move(direction, advance(direction, position), rest)
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