import gleam/list
import gleam/string

pub fn first_letter(name: String) {
  case name |> string.trim |> string.first {
    Ok(s) -> s
    _ -> ""
  }
}

pub fn initial(name: String) {
  name
  |> first_letter
  |> string.uppercase <> "."
}

pub fn initials(full_name: String) {
  case full_name
  |> string.split(" ")
  |> list.map(initial) {
    [fst, snd] -> fst <> " " <> snd
    _ -> ""
  }
}

pub fn pair(full_name1: String, full_name2: String) -> String {
  let fst = initials(full_name1)
  let snd = initials(full_name2)
"
     ******       ******
   **      **   **      **
 **         ** **         **
**            *            **
**                         **
**     " <> fst <> "  +  " <> snd <> "     **
 **                       **
   **                   **
     **               **
       **           **
         **       **
           **   **
             ***
              *
"}
