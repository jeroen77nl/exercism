import gleam/string

pub fn extract_error(problem: Result(a, b)) -> b {
  let assert Error(e) = problem
  e
}

pub fn remove_team_prefix(team: String) -> String {
  let assert Ok(#(_, rest)) = string.split_once(team, " ")
  rest
}

pub fn split_region_and_team(combined: String) -> #(String, String) {
  let assert Ok(#(region, team_long)) = string.split_once(combined, ",")
  let team_short = remove_team_prefix(team_long)
  #(region, team_short)
}
