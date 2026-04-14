import gleam/dict
// import gleam/io
import gleam/list
import gleam/option
import gleam/set

pub type Tree(a) {
  Tree(label: a, children: List(Tree(a)))
}

pub fn from_pov(tree: Tree(a), from: a) -> Result(Tree(a), Nil) {
  tree_to_dict_dfs(tree, option.None, dict.new())
  |> tree_from_dict(set.new(), from)
}

fn tree_from_dict(
  dict: dict.Dict(a, List(a)),
  processed: set.Set(a),
  from: a,
) -> Result(Tree(a), Nil) {
  case set.contains(processed, from) {
    True -> Error(Nil)
    False -> {
      let new_processed = set.insert(processed, from)
      case dict.get(dict, from) {
        Error(Nil) -> {
          Error(Nil)
        }
        Ok(goals) -> {
          Ok(Tree(
            from,
            list.filter_map(goals, fn(goal) {
              tree_from_dict(dict, new_processed, goal)
            }),
          ))
        }
      }
    }
  }
}

fn tree_to_dict_dfs(
  tree: Tree(a),
  parent: option.Option(a),
  tree_dict: dict.Dict(a, List(a)),
) -> dict.Dict(a, List(a)) {
  let label_children = list.map(tree.children, fn(child) { child.label })
  let label_nodes = case parent {
    option.None -> label_children
    option.Some(p) -> [p, ..label_children]
  }
  let extended_dict = dict.insert(tree_dict, tree.label, label_nodes)

  list.fold(tree.children, extended_dict, fn(tree_dict_acc, tree_child) {
    tree_to_dict_dfs(tree_child, option.Some(tree.label), tree_dict_acc)
  })
}

pub fn path_to(
  tree tree: Tree(a),
  from from: a,
  to to: a,
) -> Result(List(a), Nil) {
  case from_pov(tree, from) {
    Error(Nil) -> Error(Nil)
    Ok(t) -> {
      case dfs(t, to, []) {
        Error(Nil) -> Error(Nil)
        Ok(l) -> Ok(list.reverse(l))
      }
    }
  }
}

fn dfs(tree tree: Tree(a), to to: a, pad pad: List(a)) -> Result(List(a), Nil) {
  case tree.label {
    l if l == to -> Ok([l, ..pad])
    l -> dfs_list(tree.children, to, [l, ..pad])
  }
}

fn dfs_list(
  list_of_trees: List(Tree(a)),
  to: a,
  pad: List(a),
) -> Result(List(a), Nil) {
  case list_of_trees {
    [] -> Error(Nil)
    [head, ..rest] -> {
      let result = dfs(head, to, pad)
      case result {
        Error(Nil) -> dfs_list(rest, to, pad)
        _ -> result
      }
    }
  }
}
