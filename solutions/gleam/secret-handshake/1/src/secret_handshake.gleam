import gleam/int
import gleam/list

pub type Command {
  Wink
  DoubleBlink
  CloseYourEyes
  Jump
}

pub fn commands(encoded_message: Int) -> List(Command) {
  command_iter(encoded_message, 1, [])
}

fn command_iter(msg: Int, pos: Int, commands: List(Command)) -> List(Command) {
  case msg, msg % 2 {
    0, _ -> list.reverse(commands)
    _, 0 -> command_iter(msg / 2, pos + 1, commands)
    _, _ -> command_iter(msg / 2, pos + 1, add_to_commands(msg, pos, commands))
  }
}

fn add_to_commands(nr: Int, pos: Int, commands: List(Command)) -> List(Command) {
  case pos, int.bitwise_and(nr, 1) {
    1, 1 -> [Wink, ..commands]
    2, 1 -> [DoubleBlink, ..commands]
    3, 1 -> [CloseYourEyes, ..commands]
    4, 1 -> [Jump, ..commands]
    5, 1 -> list.reverse(commands)
    _, _ -> commands
  }
}
