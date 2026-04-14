import gleam/list

pub opaque type Frame {
  Frame(rolls: List(Int), bonus: List(Int))
}

pub type Game {
  Game(frames: List(Frame))
}

pub type Error {
  InvalidPinCount
  GameComplete
  GameNotComplete
}

pub fn roll(game: Game, knocked_pins: Int) -> Result(Game, Error) {
  case check_pin_count(knocked_pins) {
    False -> Error(InvalidPinCount)
    True -> add_roll_to_game(game, knocked_pins)
  }
}

fn add_roll_to_game(game: Game, knocked_pins: Int) -> Result(Game, Error) {
  case game {
    Game([]) -> Ok(Game([create_new_frame(knocked_pins)]))
    Game([head, ..rest]) -> {
      case head {
        Frame([10], _) -> {
          case list.length(game.frames) {
            10 ->
              case game.frames {
                [Frame([10], []), ..rest] ->
                  Ok(Game([add_roll_to_bonus(head, knocked_pins), ..rest]))
                [Frame([10], [10]), ..] ->
                  Ok(Game([add_roll_to_bonus(head, knocked_pins), ..rest]))
                [Frame([10], [w1]), ..] if w1 + knocked_pins <= 10 ->
                  Ok(Game([add_roll_to_bonus(head, knocked_pins), ..rest]))
                [Frame([10], [_]), ..] -> Error(InvalidPinCount)
                _ -> Error(GameComplete)
              }
            _ -> Ok(Game([create_new_frame(knocked_pins), head, ..rest]))
          }
        }
        Frame([w1], _) ->
          case check_pin_count(w1 + knocked_pins) {
            False -> Error(InvalidPinCount)
            True ->
              Ok(Game([add_snd_roll_to_frame(head, knocked_pins), ..rest]))
          }
        Frame(_, _) -> {
          case list.length(game.frames) {
            10 ->
              case head {
                Frame([w1, w2], []) if w1 + w2 == 10 ->
                  Ok(Game([add_roll_to_bonus(head, knocked_pins), ..rest]))
                _ -> Error(GameComplete)
              }
            _ -> Ok(Game([create_new_frame(knocked_pins), ..game.frames]))
          }
        }
      }
    }
  }
}

fn create_new_frame(knocked_pins: Int) -> Frame {
  Frame(rolls: [knocked_pins], bonus: [])
}

fn add_snd_roll_to_frame(frame: Frame, knocked_pins: Int) -> Frame {
  Frame(list.append(frame.rolls, [knocked_pins]), bonus: [])
}

fn add_roll_to_bonus(frame: Frame, knocked_pins: Int) -> Frame {
  case frame.bonus {
    [] -> Frame(frame.rolls, bonus: [knocked_pins])
    [first, ..] -> Frame(frame.rolls, bonus: [first, knocked_pins])
  }
}

fn check_pin_count(knocked_pins: Int) -> Bool {
  case knocked_pins {
    kp if kp > 10 -> False
    kp if kp < 0 -> False
    _ -> True
  }
}

pub fn score(game: Game) -> Result(Int, Error) {
  let frames = list.reverse(game.frames)
  case list.length(frames) {
    l if l < 10 -> Error(GameNotComplete)
    _ -> process_frames(frames, 0)
  }
}

fn process_frames(frames: List(Frame), score: Int) -> Result(Int, Error) {
  case frames {
    [last] -> {
      case last.rolls, last.bonus {
        [10], [b1, b2] -> Ok(score + 10 + b1 + b2)
        [w1, w2], [] ->
          case w1 + w2 {
            s if s < 10 -> Ok(score + w1 + w2)
            _ -> Error(GameNotComplete)
          }
        [w1, w2], [b1] ->
          case w1 + w2 {
            s if s < 10 -> Error(GameComplete)
            _ -> Ok(score + w1 + w2 + b1)
          }
        _, _ -> Error(GameNotComplete)
      }
    }
    [Frame([10], []), Frame([10], []), Frame([w1], b), ..rest] ->
      process_frames([Frame([10], []), Frame([w1], b), ..rest], score + 20 + w1)
    [Frame([10], []), Frame([10], []), Frame([w1, w2], []), ..rest] ->
      process_frames(
        [Frame([10], []), Frame([w1, w2], []), ..rest],
        score + 20 + w1,
      )
    [Frame([10], []), Frame([w1, w2], b), ..rest] ->
      process_frames([Frame([w1, w2], b), ..rest], score + 10 + w1 + w2)
    [Frame([10], []), Frame([10], [b1, b2]), ..rest] ->
      process_frames([Frame([10], [b1, b2]), ..rest], score + 10 + 10 + b1)
    [Frame([w1, w2], []), Frame([w3, w4], b), ..rest] ->
      case w1 + w2 == 10 {
        True -> process_frames([Frame([w3, w4], b), ..rest], score + 10 + w3)
        False -> process_frames([Frame([w3, w4], b), ..rest], score + w1 + w2)
      }
    [Frame([w1, w2], []), Frame([w3], b), ..rest] ->
      case w1 + w2 == 10 {
        True -> process_frames([Frame([w3], b), ..rest], score + 10 + w3)
        False -> process_frames([Frame([w3], b), ..rest], score + w1 + w2)
      }
    _ -> Error(GameNotComplete)
  }
}
