import java.util.ArrayList;
import java.util.List;

class HighScores {

    private final List<Integer> highScores = new ArrayList<>();

    public HighScores(List<Integer> highScores) {
        this.highScores.addAll(highScores);
    }

    List<Integer> scores() {
        return this.highScores;
    }

    Integer latest() {
        return this.highScores.getLast();
    }

    Integer personalBest() {
        return this.highScores.stream()
                .max(Integer::compare)
                .orElseThrow(() -> new RuntimeException("Lege lijst met scores"));
    }

    List<Integer> personalTopThree() {
        return this.highScores.stream()
                .sorted((i,j) -> Integer.compare(j, i))
                .limit(3)
                .toList();
    }

}
