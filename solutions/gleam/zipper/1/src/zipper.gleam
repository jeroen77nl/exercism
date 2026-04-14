import gleam/list

pub type Tree(a) {
  Leaf
  Node(value: a, left: Tree(a), right: Tree(a))
}

pub opaque type Zipper(a) {
  Zipper(tree: Tree(a), path: List(Direction))
}

type Direction {
  Left
  Right
}

pub fn to_zipper(tree: Tree(a)) -> Zipper(a) {
  Zipper(tree, [])
}

pub fn to_tree(zipper: Zipper(a)) -> Tree(a) {
  zipper.tree
}

pub fn value(zipper: Zipper(a)) -> Result(a, Nil) {
  let subtree = down_along_path(zipper.path, zipper.tree)
  case subtree {
    Ok(Node(val, _, _)) -> Ok(val)
    _ -> Error(Nil)
  }
}

pub fn up(zipper: Zipper(a)) -> Result(Zipper(a), Nil) {
  case zipper.path {
    [] -> Error(Nil)
    [_, ..] -> {
      Ok(Zipper(zipper.tree, shorten_path(zipper.path)))
    }
  }
}

pub fn left(zipper: Zipper(a)) -> Result(Zipper(a), Nil) {
  case down_along_path(zipper.path, zipper.tree) {
    Ok(Node(_, _, _)) -> Ok(Zipper(zipper.tree, extend_path(zipper.path, Left)))
    _ -> Error(Nil)
  }
}

fn down_along_path(
  path: List(Direction),
  subtree: Tree(a),
) -> Result(Tree(a), Nil) {
  case path {
    [] -> Ok(subtree)
    [head, ..rest] -> {
      case subtree, head {
        Node(_, l, _), Left -> down_along_path(rest, l)
        Node(_, _, r), Right -> down_along_path(rest, r)
        _, _ -> Error(Nil)
      }
    }
  }
}

pub fn right(zipper: Zipper(a)) -> Result(Zipper(a), Nil) {
  case down_along_path(zipper.path, zipper.tree) {
    Ok(Node(_, _, _)) ->
      Ok(Zipper(zipper.tree, extend_path(zipper.path, Right)))
    _ -> Error(Nil)
  }
}

pub fn set_value(zipper: Zipper(a), value: a) -> Zipper(a) {
  Zipper(set_value_iter(zipper.path, zipper.tree, [], value), zipper.path)
}

fn set_value_iter(
  target_path: List(Direction),
  current_tree: Tree(a),
  current_path: List(Direction),
  new_value: a,
) -> Tree(a) {
  case current_tree {
    Leaf -> Leaf
    Node(old_value, l, r) ->
      case current_path == target_path {
        True ->
          Node(
            new_value,
            set_value_iter(
              target_path,
              l,
              extend_path(current_path, Left),
              new_value,
            ),
            set_value_iter(
              target_path,
              r,
              extend_path(current_path, Right),
              new_value,
            ),
          )
        False ->
          Node(
            old_value,
            set_value_iter(
              target_path,
              l,
              extend_path(current_path, Left),
              new_value,
            ),
            set_value_iter(
              target_path,
              r,
              extend_path(current_path, Right),
              new_value,
            ),
          )
      }
  }
}

fn set_subtree_iter(
  target_path: List(Direction),
  current_tree: Tree(a),
  current_path: List(Direction),
  new_tree: Tree(a),
  left_right: Direction,
) -> Tree(a) {
  case current_tree {
    Leaf -> Leaf
    Node(old_value, l, r) ->
      case current_path == target_path, left_right {
        True, Left -> Node(old_value, new_tree, r)
        True, Right -> Node(old_value, l, new_tree)
        False, _ ->
          Node(
            old_value,
            set_subtree_iter(
              target_path,
              l,
              extend_path(current_path, Left),
              new_tree,
              left_right,
            ),
            set_subtree_iter(
              target_path,
              r,
              extend_path(current_path, Right),
              new_tree,
              left_right,
            ),
          )
      }
  }
}

pub fn set_left(zipper: Zipper(a), tree: Tree(a)) -> Result(Zipper(a), Nil) {
  let updated_tree = set_subtree_iter(zipper.path, zipper.tree, [], tree, Left)
  Ok(Zipper(updated_tree, zipper.path))
}

pub fn set_right(zipper: Zipper(a), tree: Tree(a)) -> Result(Zipper(a), Nil) {
  let updated_tree = set_subtree_iter(zipper.path, zipper.tree, [], tree, Right)
  Ok(Zipper(updated_tree, zipper.path))
}

fn extend_path(path: List(Direction), direction: Direction) -> List(Direction) {
  list.reverse([direction, ..list.reverse(path)])
}

fn shorten_path(path: List(Direction)) -> List(Direction) {
  list.reverse(list.drop(list.reverse(path), 1))
}
