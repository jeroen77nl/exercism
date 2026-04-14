import gleam/list

pub type Triplet {
  Triplet(Int, Int, Int)
}

pub fn triplets_with_sum(sum: Int) -> List(Triplet) {
  iter_a(1, sum, [])
}

fn iter_a(a: Int, n: Int, l: List(Triplet)) -> List(Triplet) {
  case a {
    _ if a + { a + 1 } + { a + 2 } <= n -> iter_ab(a, a + 1, n, l)
    _ if a + 1 + 1 + 1 <= n -> iter_a(a + 1, n, l)
    _ -> l
  }
}

fn iter_ab(a: Int, b: Int, n: Int, l: List(Triplet)) -> List(Triplet) {
  case a, b {
    _, _ if a + b + { b + 1 } <= n -> iter_abc(a, b, b + 1, n, l)
    _, _ if a + { b + 1 } + { b + 2 } <= n -> iter_ab(a, b + 1, n, l)
    _, _ -> iter_a(a + 1, n, l)
  }
}

fn iter_abc(a: Int, b: Int, c: Int, n: Int, l: List(Triplet)) -> List(Triplet) {
  case a + b + c {
    sum if sum == n ->
      case a * a + b * b == c * c {
        True -> iter_ab(a, b + 1, n, list.append(l, [Triplet(a, b, c)]))
        False -> iter_ab(a, b + 1, n, l)
      }
    som if som < n -> iter_abc(a, b, c + 1, n, l)
    _ -> iter_ab(a, b + 1, n, l)
  }
}
