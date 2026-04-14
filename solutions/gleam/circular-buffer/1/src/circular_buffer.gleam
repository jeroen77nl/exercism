import gleam/queue

pub opaque type CircularBuffer(t) {
  CircularBuffer(q: queue.Queue(t), capacity: Int)
}

pub fn new(capacity: Int) -> CircularBuffer(t) {
  CircularBuffer(queue.new(), capacity)
}

pub fn read(buffer: CircularBuffer(t)) -> Result(#(t, CircularBuffer(t)), Nil) {
  case queue.pop_front(buffer.q) {
    Ok(#(val, q_nw)) -> Ok(#(val, CircularBuffer(q_nw, buffer.capacity)))
    _ -> Error(Nil)
  }
}

pub fn write(
  buffer: CircularBuffer(t),
  item: t,
) -> Result(CircularBuffer(t), Nil) {
  let s = buffer.q
  case queue.length(buffer.q) == buffer.capacity {
    True -> Error(Nil)
    False -> {
      queue.push_back(s, item)
      |> CircularBuffer(buffer.capacity)
      |> Ok()
    }
  }
}

pub fn overwrite(buffer: CircularBuffer(t), item: t) -> CircularBuffer(t) {
  case queue.length(buffer.q) {
    size if size < buffer.capacity ->
      buffer.q
      |> queue.push_back(item)
      |> CircularBuffer(buffer.capacity)
    _ -> {
      let assert Ok(#(_, q_temp)) = queue.pop_front(buffer.q)
      q_temp
      |> queue.push_back(item)
      |> CircularBuffer(buffer.capacity)
    }
  }
}

pub fn clear(buffer: CircularBuffer(t)) -> CircularBuffer(t) {
  new(buffer.capacity)
}
