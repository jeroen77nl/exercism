import gleam/int
import gleam/list
import gleam/order
import gleam/string

type Team {
  Team(name: String, won: Int, draws: Int, lost: Int)
}

type TeamWithDerivableData {
  TeamWithDerivableData(
    name: String,
    played: Int,
    won: Int,
    draws: Int,
    lost: Int,
    score: Int,
  )
}

type Match {
  Match(team_name_1: String, team_name_2: String, result: MatchResult)
}

type MatchResult {
  Win
  Draw
  Loss
}

//
// public entry point
//
pub fn tally(input: String) -> String {
  let matches = process_input(input)
  init_teams(matches)
  |> process_matches(matches)
  |> make_derivable_teams
  |> sort_teams
  |> generate_table
}

//
// process input and convert to custom types
//
fn process_input(input: String) -> List(Match) {
  case input {
    "" -> []
    _ ->
      input
      |> string.split(on: "\n")
      |> list.map(input_line_to_match)
  }
}

fn input_line_to_match(input_line: String) -> Match {
  let assert [team1, team2, result] = string.split(input_line, ";")
  Match(team1, team2, parse_match_result(result))
}

fn init_teams(matches: List(Match)) -> List(Team) {
  list.map(matches, fn(m) { m.team_name_1 })
  |> list.append(list.map(matches, fn(m) { m.team_name_2 }))
  |> list.unique
  |> list.map(fn(name) { Team(name, 0, 0, 0) })
}

fn process_matches(teams: List(Team), matches: List(Match)) -> List(Team) {
  teams
  |> list.map(fn(team: Team) {
    matches
    |> list.fold(from: team, with: fn(team, match) {
      case team.name, match.team_name_1, match.team_name_2, match.result {
        tn, mn1, _, Win if tn == mn1 ->
          Team(tn, team.won + 1, team.draws, team.lost)
        tn, mn1, _, Draw if tn == mn1 ->
          Team(tn, team.won, team.draws + 1, team.lost)
        tn, mn1, _, Loss if tn == mn1 ->
          Team(tn, team.won, team.draws, team.lost + 1)
        tn, _, mn2, Loss if tn == mn2 ->
          Team(tn, team.won + 1, team.draws, team.lost)
        tn, _, mn2, Draw if tn == mn2 ->
          Team(tn, team.won, team.draws + 1, team.lost)
        tn, _, mn2, Win if tn == mn2 ->
          Team(tn, team.won, team.draws, team.lost + 1)
        _, _, _, _ -> team
      }
    })
  })
}

fn make_derivable_teams(teams: List(Team)) -> List(TeamWithDerivableData) {
  teams
  |> list.map(fn(t) {
    TeamWithDerivableData(
      t.name,
      team_plays(t),
      t.won,
      t.draws,
      t.lost,
      team_score(t),
    )
  })
}

fn sort_teams(teams: List(TeamWithDerivableData)) -> List(TeamWithDerivableData) {
  teams
  |> list.sort(by: fn(t1, t2) {
    let sort_key_score = int.compare(t2.score, t1.score)
    case sort_key_score {
      order.Eq -> string.compare(t1.name, t2.name)
      _ -> sort_key_score
    }
  })
}

fn generate_table(teams: List(TeamWithDerivableData)) -> String {
  let sep = " | "
  let header = ["Team                           | MP |  W |  D |  L |  P"]
  let team_lines =
    teams
    |> list.map(fn(t) {
      string.pad_right(t.name, 30, " ")
      <> sep
      <> display_int(t.played)
      <> sep
      <> display_int(t.won)
      <> sep
      <> display_int(t.draws)
      <> sep
      <> display_int(t.lost)
      <> sep
      <> display_int(t.score)
    })

  header
  |> list.append(team_lines)
  |> string.join("\n")
}

//
// helper functions
//
fn team_score(team: Team) -> Int {
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
