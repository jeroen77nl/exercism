import java.util.HashMap;
import java.util.Map;

class NucleotideCounter {

    private final String sequence;

    NucleotideCounter(String sequence) {
        for (char c : sequence.toCharArray()) {
            if ("ACTG".indexOf(c) == -1) {
                throw new IllegalArgumentException();
            }
        }

        this.sequence = sequence;
    }

    Map<Character, Integer> nucleotideCounts() {

        Map<Character, Integer> result = new HashMap<>();
        result.put('A', 0);
        result.put('C', 0);
        result.put('G', 0);
        result.put('T', 0);

        for (char c : sequence.toCharArray()) {
            result.put(c, result.get(c) + 1);
        }

        return Map.copyOf(result);
    }
}