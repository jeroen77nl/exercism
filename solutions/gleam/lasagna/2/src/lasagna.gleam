pub fn expected_minutes_in_oven() -> Int {
  40
}

pub fn remaining_minutes_in_oven(actual_minutes: Int) -> Int{
  expected_minutes_in_oven() - actual_minutes
}

pub fn preparation_time_in_minutes(nr_of_layers: Int) -> Int {
  2 * nr_of_layers
}

pub fn total_time_in_minutes(
    nr_of_layers: Int, 
    minutes_in_oven: Int
) -> Int {
  preparation_time_in_minutes(nr_of_layers) + minutes_in_oven
}

pub fn alarm() -> String {
  "Ding!"
}