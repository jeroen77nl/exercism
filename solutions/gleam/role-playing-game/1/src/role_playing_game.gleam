import gleam/int
import gleam/option.{type Option, None, Some}

pub type Player {
  Player(name: Option(String), level: Int, health: Int, mana: Option(Int))
}

pub fn introduce(player: Player) -> String {
  case player.name {
    Some(name) -> name
    None -> "Mighty Magician"
  }
}

pub fn revive(player: Player) -> Option(Player) {
  case player {
    Player(health: h, ..) if h == 0 ->
      case player {
        Player(level: l, ..) if l >= 10 ->
          Some(Player(..player, health: 100, mana: Some(100)))
        _ -> Some(Player(..player, health: 100))
      }
    _ -> None
  }
}

pub fn cast_spell(player: Player, cost: Int) -> #(Player, Int) {
  case player.mana {
    None -> #(Player(..player, health: int.max(player.health - cost, 0)), 0)
    Some(mana) if mana < cost -> #(player, 0)
    Some(mana) -> #(Player(..player, mana: Some(mana - cost)), 2 * cost)
  }
}
