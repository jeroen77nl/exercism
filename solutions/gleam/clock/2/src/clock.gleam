import gleam/int
import gleam/string

pub type Clock {
  Clock(hour: Int, minute: Int)
}

pub fn create(hour hour: Int, minute minute: Int) -> Clock {
  create_min(hour * 60 + minute)
}

fn create_min(min: Int) -> Clock {
  case min {
    min if min < 0 -> create_min(min + 1440)
    _ -> Clock(min / 60 % 24, min % 60) 
  }
}

pub fn add(clock: Clock, minutes minutes: Int) -> Clock {
  create_min(clock.hour * 60 + clock.minute + minutes)
}

pub fn subtract(clock: Clock, minutes minutes: Int) -> Clock {
  create_min(clock.hour * 60 + clock.minute - minutes)
}

pub fn display(clock: Clock) -> String {
  let pad = fn(n) { 
    string.pad_left(int.to_string(n), 2, "0")
  }
  pad(clock.hour) <> ":" <> pad(clock.minute)
}