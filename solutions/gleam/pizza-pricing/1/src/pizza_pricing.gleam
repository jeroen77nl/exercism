import gleam/list

pub type Pizza {
    Margherita
    Caprese
    Formaggio
    ExtraSauce(Pizza)
    ExtraToppings(Pizza)
}

pub fn pizza_price(pizza: Pizza) -> Int {
  pizza_price_iter(pizza, 0)
}

fn pizza_price_iter(pizza: Pizza, total_price: Int) -> Int {
  case pizza {
    Margherita -> 7 + total_price
    Caprese -> 9 + total_price
    Formaggio -> 10 + total_price
    ExtraSauce(p) -> pizza_price_iter(p, total_price + 1)
    ExtraToppings(p) -> pizza_price_iter(p, total_price + 2)
  }
}

pub fn order_price(order: List(Pizza)) -> Int {
    case list.length(order) {
        1 -> order_price_iter(order, 3)
        2 -> order_price_iter(order, 2)
        _ -> order_price_iter(order, 0)
    }
}

fn order_price_iter(order: List(Pizza), total_price: Int) {
    case order {
        [] -> total_price
        [head, ..rest] -> order_price_iter(rest, pizza_price(head) + total_price)
    }
}
