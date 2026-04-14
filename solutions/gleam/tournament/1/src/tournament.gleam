import gleam/int
import gleam/list
import gleam/order
import gleam/string

pub fn tally(input: String) -> String {
  let matches = case input {
    "" -> []
    _ ->
      input
      |> string.split(on: "\n")
      |> input_to_matches
  }
  let team_lines =
    matches
    |> process_matches(init_teams(matches))
    |> list.sort(by: fn(t1, t2) {
      let key1 = int.compare(team_points(t2), team_points(t1))
      case key1 {
        order.Eq -> string.compare(t1.name, t2.name)
        _ -> key1
      }
    })

  maak_overzicht(team_lines)
  //   |> create_table
  //   |> string.join("\n")
}

type Team {
  Team(name: String, won: Int, draws: Int, lost: Int)
}

type Match {
  Match(team_name_1: String, team_name_2: String, result: MatchResult)
}

type MatchResult {
  Win
  Draw
  Loss
}

fn init_teams(matches: List(Match)) -> List(Team) {
  list.map(matches, fn(m) { m.team_name_1 })
  |> list.append(list.map(matches, fn(m) { m.team_name_2 }))
  |> list.unique
  |> list.map(fn(name) { Team(name, 0, 0, 0) })
}

fn process_matches(matches: List(Match), teams: List(Team)) -> List(Team) {
  teams
  |> list.map(fn(team: Team) {
    matches
    |> list.fold(from: team, with: fn(team, match) {
      case team.name, match.team_name_1, match.team_name_2, match.result {
        tn, mn1, _mn2, Win if tn == mn1 ->
          Team(tn, team.won + 1, team.draws, team.lost)
        tn, mn1, _mn2, Draw if tn == mn1 ->
          Team(tn, team.won, team.draws + 1, team.lost)
        tn, mn1, _mn2, Loss if tn == mn1 ->
          Team(tn, team.won, team.draws, team.lost + 1)
        tn, _mn1, mn2, Loss if tn == mn2 ->
          Team(tn, team.won + 1, team.draws, team.lost)
        tn, _mn1, mn2, Draw if tn == mn2 ->
          Team(tn, team.won, team.draws + 1, team.lost)
        tn, _mn1, mn2, Win if tn == mn2 ->
          Team(tn, team.won, team.draws, team.lost + 1)
        _, _, _, _ -> team
      }
    })
  })
}

fn maak_overzicht(teams: List(Team)) -> String {
  let header = ["Team                           | MP |  W |  D |  L |  P"]

  let team_lines =
    teams
    |> list.map(fn(team) {
      string.pad_right(team.name, 30, " ")
      <> " | "
      <> display_int(team_plays(team))
      <> " | "
      <> display_int(team.won)
      <> " | "
      <> display_int(team.draws)
      <> " | "
      <> display_int(team.lost)
      <> " | "
      <> display_int(team_points(team))
    })

  header
  |> list.append(team_lines)
  |> string.join("\n")
}

fn input_to_matches(matches_in_csv: List(String)) -> List(Match) {
  matches_in_csv
  |> list.map(input_line_to_match)
}

fn input_line_to_match(input_line: String) -> Match {
  let assert [team1, team2, result] = string.split(input_line, ";")
  Match(team1, team2, parse_match_result(result))
}

fn team_points(team: Team) -> Int {
  team.won * 3 + team.draws
}

fn team_plays(team: Team) -> Int {
  team.won + team.draws + team.lost
}

fn parse_match_result(result: String) -> MatchResult {
  case result {
    "win" -> Win
    "draw" -> Draw
    _ -> Loss
  }
}

fn display_int(value: Int) -> String {
  string.pad_left(int.to_string(value), 2, " ")
}
