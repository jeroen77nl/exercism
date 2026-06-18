import java.util.*;

enum ResultEnum {
    WIN(new ScoreDelta(1, 0, 0, 3)),
    LOSS(new ScoreDelta(0, 0, 1, 0)),
    DRAW(new ScoreDelta(0, 1, 0, 1));

    final ScoreDelta delta;

    ResultEnum(ScoreDelta delta) {
        this.delta = delta;
    }
}

record Match(String homeTeam, String guestTeam, ResultEnum result) {
}

record TeamScore(String name, int matches, int won, int draws, int lost, int points) {
}

record ScoreDelta(int won, int draws, int lost, int points) {
}

class Tournament {

    private static final Comparator<TeamScore> STANDINGS_ORDER =
            Comparator.comparingInt(TeamScore::points)
                    .reversed()
                    .thenComparing(TeamScore::name);

    private final Map<String, TeamScore> teamScores = new HashMap<>();

    String printTable() {
        String header = "Team                           | MP |  W |  D |  L |  P";
        List<String> rows = teamScores.values()
                .stream()
                .sorted(STANDINGS_ORDER)
                .map(this::formatTeamRow)
                .toList();

        return header + "\n" + String.join("", rows);
    }

    void applyResults(String resultString) {
        List<Match> matches = makeMatches(resultString);
        updateScores(matches);
    }

    private List<Match> makeMatches(String resultString) {
        if (resultString.isBlank()) {
            return List.of();
        }

        List<Match> matches = new ArrayList<>();
        String[] matchLines = resultString.split("\n");
        for (String matchLine : matchLines) {
            String[] matchDetails = matchLine.split(";");
            if (matchDetails.length != 3) {
                throw new IllegalArgumentException(
                        "Invalid match line: " + matchLine);
            }
            Match match = new Match(
                    matchDetails[0],
                    matchDetails[1],
                    parseResult(matchDetails[2])
            );
            matches.add(match);
        }
        return matches;
    }

    private ResultEnum parseResult(String value) {
        return switch (value) {
            case "win" -> ResultEnum.WIN;
            case "loss" -> ResultEnum.LOSS;
            case "draw" -> ResultEnum.DRAW;
            default -> throw new IllegalArgumentException(
                    "Unknown result: " + value);
        };
    }

    private void updateScores(List<Match> matches) {
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

    private TeamScore updateTeam(TeamScore teamScore, ResultEnum result) {
        ScoreDelta d = result.delta;

        return new TeamScore(
                teamScore.name(),
                teamScore.matches() + 1,
                teamScore.won() + d.won(),
                teamScore.draws() + d.draws(),
                teamScore.lost() + d.lost(),
                teamScore.points() + d.points()
        );
    }

    private ResultEnum opposite(ResultEnum result) {
        return switch (result) {
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

}
