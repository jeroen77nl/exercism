import gleam/dict
import gleam/list
import gleam/set

pub fn can_chain(chain: List(#(Int, Int))) -> Bool {
  let result = case chain {
    [] -> True
    [stone] -> stone.0 == stone.1
    _ -> can_chain_iter(chain, dict.new())
  }

  let al = make_adjacency_list(chain)
  result && is_connected(al)
}

fn can_chain_iter(chain: List(#(Int, Int)), nodes: dict.Dict(Int, Int)) -> Bool {
  case chain {
    [] -> check_counts(nodes)
    [#(fst, snd), ..rest] -> {
      nodes
      |> add_to_nodes(fst)
      |> add_to_nodes(snd)
      |> can_chain_iter(rest, _)
    }
  }
}

fn check_counts(nodes: dict.Dict(Int, Int)) -> Bool {
  list.all(dict.to_list(nodes), fn(node) {
    let #(_, counter) = node
    counter % 2 == 0
  })
}

fn add_to_nodes(nodes: dict.Dict(Int, Int), val: Int) -> dict.Dict(Int, Int) {
  case dict.get(nodes, val) {
    Ok(aantal) -> dict.insert(nodes, val, aantal + 1)
    Error(Nil) -> dict.insert(nodes, val, 1)
  }
}

type Graph =
  dict.Dict(Int, List(Int))

fn dfs(graph: Graph, node: Int, visited: set.Set(Int)) -> set.Set(Int) {
  case set.contains(visited, node) {
    True -> visited
    False -> {
      let neighbors = case dict.get(graph, node) {
        Error(Nil) -> []
        Ok(neighbors) -> neighbors
      }
      let visited_with_node = set.insert(visited, node)
      list.fold(neighbors, visited_with_node, fn(visited, neighbor) {
        dfs(graph, neighbor, visited)
      })
    }
  }
}

fn is_connected(graph: Graph) -> Bool {
  let nodes =
    dict.keys(graph)
    |> list.unique
  case list.first(nodes) {
    Ok(start_node) -> {
      let visited = dfs(graph, start_node, set.new())
      list.length(nodes) == set.size(visited)
    }
    Error(_) -> True
  }
}

fn make_adjacency_list(chain: List(#(Int, Int))) -> Graph {
  list.fold(chain, dict.new(), fn(acc, stone) {
    let #(key1, key2) = stone
    let acc1 = case dict.get(acc, key1) {
      Ok(list) -> dict.insert(acc, key1, list.unique([key2, ..list]))
      Error(Nil) -> dict.insert(acc, key1, [key2])
    }
    case dict.get(acc1, key2) {
      Ok(list) -> dict.insert(acc1, key2, list.unique([key1, ..list]))
      Error(Nil) -> dict.insert(acc1, key2, [key1])
    }
  })
}
