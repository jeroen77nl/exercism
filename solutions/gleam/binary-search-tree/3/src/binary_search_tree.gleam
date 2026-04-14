import gleam/list

pub type Tree {
  Nil
  Node(data: Int, left: Tree, right: Tree)
}

pub fn to_tree(data: List(Int)) -> Tree {
    // fold([1, 2, 3], Nil, insert_node) is the equivalent of 
    // insert_node(insert_node(insert_node(Nil, 1), 2), 3)
  list.fold(data, Nil, insert_node)
}

fn insert_node(tree: Tree, value: Int) -> Tree {
  case tree {
    Nil -> Node(value, Nil, Nil)
    Node(data, left, right) if value <= data ->
      Node(data, insert_node(left, value), right)
    Node(data, left, right) -> Node(data, left, insert_node(right, value))
  }
}

pub fn sorted_data(data: List(Int)) -> List(Int) {
  let tree = to_tree(data)
  list_tree(tree)
}

fn list_tree(tree) -> List(Int) {
  case tree {
    Nil -> []
    Node(data, left, right) -> {
      list_tree(left)
      |> list.append([data])
      |> list.append(list_tree(right))
    }
  }
}
