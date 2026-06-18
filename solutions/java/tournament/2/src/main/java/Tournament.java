import java.util.*;

enum ResultEnum {
    WIN, LOSS, DRAW
}

record Match(String homeTeam, String guestTeam, ResultEnum result) {
}

record TeamScore(String name, int matches, int won, int draws, int lost, int points) {
}

class Tournament {

    private Map<String, TeamScore> teamScores = new HashMap<>();

    String printTable() {
        String header = "Team                           | MP |  W |  D |  L |  P";
        List<String> rows = teamScores.values()
                .stream()
                .sorted(Comparator.comparingInt(TeamScore::points)
                        .reversed()
                        .thenComparing(TeamScore::name))
                .map(this::formatTeamRow)
                .toList();

        return header + "\n" + String.join("", rows);
    }

    private ResultEnum opposite(ResultEnum result) {
        return switch(result) {
            case WIN -> ResultEnum.LOSS;
            case LOSS -> ResultEnum.WIN;
            case DRAW -> ResultEnum.DRAW;
        };
    }

    private String formatTeamRow(TeamScore teamScore) {
        return "%-30s | %2d | %2d | %2d | %2d | %2d%n".formatted(
                teamScore.name(),
                teamScore.matches(),
                teamScore.won(),
                teamScore.draws(),
                teamScore.lost(),
                teamScore.points()
        );
    }

    void applyResults(String resultString) {
        List<Match> matches = makeMatches(resultString);
        matchesToScore(matches);
    }

    private TeamScore updateTeam(TeamScore teamScore, ResultEnum result) {
        return switch (result) {
            case ResultEnum.WIN ->
                    new TeamScore(
                            teamScore.name(),
                            teamScore.matches() + 1,
                            teamScore.won() + 1,
                            teamScore.draws(),
                            teamScore.lost(),
                            teamScore.points() + 3
                    );
            case ResultEnum.DRAW ->
                    new TeamScore(
                            teamScore.name(),
                            teamScore.matches() + 1,
                            teamScore.won(),
                            teamScore.draws() + 1,
                            teamScore.lost(),
                            teamScore.points() + 1);
            case ResultEnum.LOSS ->
                    new TeamScore(
                            teamScore.name(),
                            teamScore.matches() + 1,
                            teamScore.won(),
                            teamScore.draws(),
                            teamScore.lost() + 1,
                            teamScore.points());
        };
    }

    private void matchesToScore(List<Match> matches) {
        for (Match match : matches) {
            teamScores.compute(match.homeTeam(), (name, old) -> {
                if (old == null) {
                    old = new TeamScore(name, 0, 0, 0, 0, 0);
                }
                return updateTeam(old, match.result());
            });

            teamScores.compute(match.guestTeam(), (name, old) -> {
                if (old == null) {
                    old = new TeamScore(name, 0, 0, 0, 0, 0);
                }
                return updateTeam(old, opposite(match.result()));
            });
        }
    }

    private List<Match> makeMatches(String resultString) {
        List<Match> matches = new ArrayList<>();
        String[] matchLines = resultString.split("\n");
        for (String matchLine : matchLines) {
            String[] matchDetails = matchLine.split(";");
            Match match = new Match(
                    matchDetails[0],
                    matchDetails[1],
                    switch (matchDetails[2]) {
                        case "win" -> ResultEnum.WIN;
                        case "loss" -> ResultEnum.LOSS;
                        case "draw" -> ResultEnum.DRAW;
                        default -> throw new IllegalArgumentException(
                                "Unknown result: " + matchDetails[2]);
                    }            );
            matches.add(match);
        }
        return matches;
    }
}
