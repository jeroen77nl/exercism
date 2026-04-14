import gleam/queue.{type Queue}

pub fn insert_top(queue: Queue(Int), card: Int) -> Queue(Int) {
  queue
  |> queue.push_back(card)
}

pub fn remove_top_card(queue: Queue(Int)) -> Queue(Int) {
  queue
  |> remove(queue.pop_back)
}

pub fn insert_bottom(queue: Queue(Int), card: Int) -> Queue(Int) {
  queue
  |> queue.push_front(card)
}

pub fn remove_bottom_card(queue: Queue(Int)) -> Queue(Int) {
  queue 
  |> remove(queue.pop_front)
}

pub fn check_size_of_stack(queue: Queue(Int), target: Int) -> Bool {
  queue.length(queue) == target
}

fn remove(
  queue: Queue(Int),
  qf: fn(Queue(Int)) -> Result(#(Int, Queue(Int)), Nil),
) -> Queue(Int) {
  case qf(queue) {
    Ok(#(_, q_new)) -> q_new
    _ -> queue
  }
}
