import gleam/list
import gleam/string

pub type Student {
  Alice
  Bob
  Charlie
  David
  Eve
  Fred
  Ginny
  Harriet
  Ileana
  Joseph
  Kincaid
  Larry
}

pub type Plant {
  Radishes
  Clover
  Violets
  Grass
}

const students: List(Student) = [
  Alice, Bob, Charlie, David, Eve, Fred, Ginny, Harriet, Ileana, Joseph, Kincaid,
  Larry,
]

pub fn plants(diagram: String, student: Student) -> List(Plant) {
  let assert Ok(#(_student, planten)) =
    list.zip(students, parse_diagram_to_plant_strings(diagram))
    |> list.find(one_that: fn(pair) { pair.0 == student })
  planten
}

fn parse_diagram_to_plant_strings(diagram: String) -> List(List(Plant)) {
  let assert [s1, s2] = string.split(diagram, "\n")
  splits_regels_naar_plant_string(
    string.to_graphemes(s1),
    string.to_graphemes(s2),
  )
  |> list.map(plant_string_of_four_to_list_of_plants)
}

fn splits_regels_naar_plant_string(
  graphemes1: List(String),
  graphemes2: List(String),
) -> List(String) {
  case graphemes1, graphemes2 {
    [pos1a, pos1b, ..rest1], [pos2a, pos2b, ..rest2] -> {
      let plant_string = pos1a <> pos1b <> pos2a <> pos2b
      [plant_string, ..splits_regels_naar_plant_string(rest1, rest2)]
    }
    _, _ -> []
  }
}

fn plant_string_of_four_to_list_of_plants(plant_string_4: String) -> List(Plant) {
  plant_string_4
  |> string.to_graphemes
  |> list.map(string_to_plant)
}

fn string_to_plant(plant_string: String) -> Plant {
  case plant_string {
    "R" -> Radishes
    "C" -> Clover
    "V" -> Violets
    _ -> Grass
  }
}
