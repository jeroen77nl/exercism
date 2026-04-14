import gleam/int
import gleam/io
import gleam/list

pub opaque type Set(t) {
  Set(s: List(t))
}

pub fn new(members: List(t)) -> Set(t) {
  members
  |> list.append(list.new())
  |> Set()
}

pub fn is_empty(set: Set(t)) -> Bool {
  list.is_empty(set.s)
}

pub fn contains(in set: Set(t), this member: t) -> Bool {
  set.s
  |> list.contains(member)
}

pub fn is_subset(first: Set(t), of second: Set(t)) -> Bool {
  list.fold(first.s, True, fn(acc, member) {
    acc && second.s |> list.contains(member)
  })
}

pub fn disjoint(first: Set(t), second: Set(t)) -> Bool {
  !list.fold(first.s, False, fn(acc, member) {
    acc || second.s |> list.contains(member)
  })
}

pub fn is_equal(first: Set(t), to second: Set(t)) -> Bool {
  is_subset(first, second) && is_subset(second, first)
}

pub fn add(to set: Set(t), this member: t) -> Set(t) {
  case contains(set, member) {
    True -> set
    False -> Set([member, ..set.s])
  }
}

pub fn intersection(of first: Set(t), and second: Set(t)) -> Set(t) {
  list.fold(first.s, [], fn(acc, f) {
    case list.contains(second.s, f) {
        True -> [f, ..acc]
        False -> acc
    }
  })
  |> Set()
}

pub fn difference(between first: Set(t), and second: Set(t)) -> Set(t) {
  list.fold(first.s, [], fn(acc, f) {
    case list.contains(second.s, f) {
        False -> [f, ..acc]
        True -> acc
    }
  })
  |> Set()  
}

pub fn union(of first: Set(t), and second: Set(t)) -> Set(t) {
  list.unique(list.append(first.s, second.s)) 
  |> Set()
}
