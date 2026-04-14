import gleam/int
import gleam/string

pub type Clock {
  Clock(hour: Int, minute: Int)
}

pub fn create(hour hour: Int, minute minute: Int) -> Clock {
  normalize(Clock(hour, minute))
}

pub fn add(clock: Clock, minutes minutes: Int) -> Clock {
  normalize(Clock(clock.hour, clock.minute + minutes))
}

pub fn subtract(clock: Clock, minutes minutes: Int) -> Clock {
  normalize(Clock(clock.hour, clock.minute - minutes))
}

pub fn display(clock: Clock) -> String {
  string.concat([
    lpad_zero(clock.hour), 
    ":", 
    lpad_zero(clock.minute)
  ])
}

fn lpad_zero(value: Int) {
  case value < 10 {
    True -> string.concat(["0", int.to_string(value)])
    False -> int.to_string(value)
  }
}

fn normalize(clock: Clock) -> Clock {
  normalize_hours(normalize_minutes(clock))
}

fn normalize_minutes(clock: Clock) -> Clock {
  case clock.minute < 0 {
    True -> {
      case clock.minute % 60 == 0 {
        True -> Clock(clock.hour + clock.minute / 60, 0)
        False -> Clock(clock.hour + clock.minute / 60 - 1, clock.minute % 60 + 60)
      }
    }
    False -> case clock.minute > 59 {
      True -> Clock(clock.hour + clock.minute / 60, clock.minute % 60)
      False -> Clock(clock.hour, clock.minute)     
    }
  }
}

fn normalize_hours(clock: Clock) -> Clock {
  case clock.hour < 0 {
    True -> {
      case clock.hour % 24 == 0 {
        True -> Clock(0, clock.minute)
        False -> Clock(clock.hour % 24 + 24, clock.minute)
      }
    }
    False -> case clock.hour > 23 {
      True -> Clock(clock.hour % 24, clock.minute)
      False -> clock
    }
  }
}