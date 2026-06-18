import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;

enum ResultEnum {
    WIN, LOSS, DRAW;
}

record Match(String homeTeam, String guestTeam, ResultEnum result) {
}

record TeamScore(String name, int matches, int won, int draws, int lost, int points) {
}

class Tournament {

    List<Match> matches = new ArrayList<>();
    Map<String, TeamScore> teamScores = new HashMap<>();

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
        makeMatches(resultString);
        matchesToScore();
    }

    private void matchesToScore() {
        for (Match match : matches) {
            TeamScore old = teamScores.getOrDefault(match.homeTeam()
                    , new TeamScore(match.homeTeam(), 0, 0, 0, 0, 0));
            teamScores.put(
                    old.name(),
                    switch (match.result()) {
                        case ResultEnum.WIN ->
                                new TeamScore(old.name(), old.matches() + 1, old.won() + 1, old.draws(), old.lost(), old.points() + 3);
                        case ResultEnum.DRAW ->
                                new TeamScore(old.name(), old.matches() + 1, old.won(), old.draws() + 1, old.lost(), old.points() + 1);
                        case ResultEnum.LOSS ->
                                new TeamScore(old.name(), old.matches() + 1, old.won(), old.draws(), old.lost() + 1, old.points());
                    }
            );

            old = teamScores.getOrDefault(match.guestTeam()
                    , new TeamScore(match.guestTeam(), 0, 0, 0, 0, 0));
            teamScores.put(
                    old.name(),
                    switch (match.result()) {
                        case ResultEnum.LOSS ->
                                new TeamScore(old.name(), old.matches() + 1, old.won() + 1, old.draws(), old.lost(), old.points() + 3);
                        case ResultEnum.DRAW ->
                                new TeamScore(old.name(), old.matches() + 1, old.won(), old.draws() + 1, old.lost(), old.points() + 1);
                        case ResultEnum.WIN ->
                                new TeamScore(old.name(), old.matches() + 1, old.won(), old.draws(), old.lost() + 1, old.points());
                    }
            );
        }
    }

    private void makeMatches(String resultString) {
        String[] matchLines = resultString.split("\n");
        for (String matchLine : matchLines) {
            String[] matchDetails = matchLine.split(";");
            Match match = new Match(
                    matchDetails[0],
                    matchDetails[1],
                    switch (matchDetails[2]) {
                        case "win" -> ResultEnum.WIN;
                        case "loss" -> ResultEnum.LOSS;
                        default -> ResultEnum.DRAW;
                    }
            );
            matches.add(match);
        }
    }
}
