import gleam/io
import gleam/list

pub type Tree(a) {
  Nil
  Node(value: a, left: Tree(a), right: Tree(a))
}

pub type Error {
  DifferentLengths
  DifferentItems
  NonUniqueItems
}

pub fn tree_from_traversals(
  inorder inorder: List(a),
  preorder preorder: List(a),
) -> Result(Tree(a), Error) {
  case validate_length(inorder, preorder) {
    False -> Error(DifferentLengths)
    True ->
      case duplicate_elements(inorder, preorder) {
        False -> Error(NonUniqueItems)
        True -> case same_elements(inorder, preorder) {
            False -> Error(DifferentItems)
            True -> Ok(traverse(inorder, preorder))
          }
      }
  }
}

fn traverse(inorder inorder: List(a), preorder preorder: List(a)) -> Tree(a) {
  case preorder {
    [] -> Nil
    [val, ..rest_preorder] -> {
      let links_inorder = list.take_while(inorder, fn(x) { x != val })
      let rechts_inorder =
        inorder
        |> list.drop_while(fn(x) { x != val })
        |> list.drop_while(fn(x) { x == val })

      let links_preorder = list.take(rest_preorder, list.length(links_inorder))
      let rechts_preorder = list.drop(rest_preorder, list.length(links_inorder))

      let boom_links = traverse(links_inorder, links_preorder)
      let boom_rechts = traverse(rechts_inorder, rechts_preorder)

      Node(val, boom_links, boom_rechts)
    }
  }
}

fn validate_length(inorder: List(a), preorder: List(a)) -> Bool {
  list.length(inorder) == list.length(preorder)
}

fn duplicate_elements(inorder: List(a), preorder: List(a)) -> Bool {
  list.length(inorder) == list.length(list.unique(inorder))
  && list.length(preorder) == list.length(list.unique(preorder))
}

fn same_elements(inorder: List(a), preorder: List(a)) -> Bool {
    list.fold(inorder, True, fn(acc, in_item) { acc && list.contains(preorder, in_item)})
}

pub fn main() {
  traverse([5, 3, 9, 10, 1, 2, 6, 7], [10, 3, 5, 9, 2, 1, 6, 7])
  |> io.debug
  traverse(["i", "a", "f", "x", "r"], ["a", "i", "x", "f", "r"])
  |> io.debug
}
