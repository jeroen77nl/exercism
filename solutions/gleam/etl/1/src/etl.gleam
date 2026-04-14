import gleam/string
import gleam/list
import gleam/dict.{type Dict}

pub fn transform(legacy: Dict(Int, List(String))) -> Dict(String, Int) {
  list.fold(
    dict.to_list(legacy), 
    dict.new(), 
    invert)
}

fn invert(d: Dict(String, Int), tpl: #(Int, List(String))) -> Dict(String, Int) {
  let #(val, letters) = tpl
  list.fold(
    letters, 
    d, 
    fn(d, letter) {
      dict.insert(d, string.lowercase(letter), val)
    }
  )
}