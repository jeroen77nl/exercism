pub type TreasureChest(a) {
  TreasureChest(password: String, treasure: a)
}

pub type UnlockResult(a) {
  Unlocked(a)
  WrongPassword
}

pub fn get_treasure(
  chest: TreasureChest(treasure_chest),
  password: String,
) -> UnlockResult(treasure_chest) {
  case chest {
    TreasureChest(password: p, treasure: t) if p == password -> Unlocked(t)
    _ -> WrongPassword
  }
}
