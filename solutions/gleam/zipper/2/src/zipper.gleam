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
  let subtree = follow_path(zipper.path, zipper.tree)
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
  case follow_path(zipper.path, zipper.tree) {
    Ok(Node(_, _, _)) -> Ok(Zipper(zipper.tree, extend_path(zipper.path, Left)))
    _ -> Error(Nil)
  }
}

pub fn right(zipper: Zipper(a)) -> Result(Zipper(a), Nil) {
  case follow_path(zipper.path, zipper.tree) {
    Ok(Node(_, _, _)) ->
      Ok(Zipper(zipper.tree, extend_path(zipper.path, Right)))
    _ -> Error(Nil)
  }
}

pub fn set_value(zipper: Zipper(a), value: a) -> Zipper(a) {
  Zipper(set_value_iter(zipper.path, zipper.tree, [], value), zipper.path)
}

pub fn set_left(zipper: Zipper(a), tree: Tree(a)) -> Result(Zipper(a), Nil) {
  let updated_tree = set_tree(zipper.path, zipper.tree, [], tree, Left)
  Ok(Zipper(updated_tree, zipper.path))
}

pub fn set_right(zipper: Zipper(a), tree: Tree(a)) -> Result(Zipper(a), Nil) {
  let updated_tree = set_tree(zipper.path, zipper.tree, [], tree, Right)
  Ok(Zipper(updated_tree, zipper.path))
}

fn extend_path(path: List(Direction), direction: Direction) -> List(Direction) {
  list.reverse([direction, ..list.reverse(path)])
}

fn shorten_path(path: List(Direction)) -> List(Direction) {
  list.reverse(list.drop(list.reverse(path), 1))
}

fn follow_path(path: List(Direction), subtree: Tree(a)) -> Result(Tree(a), Nil) {
  case path {
    [] -> Ok(subtree)
    [head, ..rest] -> {
      case subtree, head {
        Node(_, l, _), Left -> follow_path(rest, l)
        Node(_, _, r), Right -> follow_path(rest, r)
        _, _ -> Error(Nil)
      }
    }
  }
}

fn set_value_iter(
  target_path: List(Direction),
  tree: Tree(a),
  path: List(Direction),
  new_value: a,
) -> Tree(a) {
  case tree {
    Leaf -> Leaf
    Node(old_value, l, r) -> {
      let value = case path == target_path {
        True -> new_value
        False -> old_value
      }
      Node(
        value,
        set_value_iter(target_path, l, extend_path(path, Left), new_value),
        set_value_iter(target_path, r, extend_path(path, Right), new_value),
      )
    }
  }
}

fn set_tree(
  target_path: List(Direction),
  tree: Tree(a),
  path: List(Direction),
  new_tree: Tree(a),
  dir: Direction,
) -> Tree(a) {
  case tree {
    Leaf -> Leaf
    Node(v, l, r) ->
      case path == target_path, dir {
        True, Left -> Node(v, new_tree, r)
        True, Right -> Node(v, l, new_tree)
        False, _ ->
          Node(
            v,
            set_tree(target_path, l, extend_path(path, Left), new_tree, dir),
            set_tree(target_path, r, extend_path(path, Right), new_tree, dir),
          )
      }
  }
}
