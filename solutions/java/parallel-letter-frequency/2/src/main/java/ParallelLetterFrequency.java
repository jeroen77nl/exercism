import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ParallelLetterFrequency {

    private final String[] texts;
    Map<Character, Integer> result;

    Set<Character> skipSet = Set.of(
            ' ', '\t', '\n', '\r', '!', '?', ';',
            ',', '.', '-', ':', '"', '\'', '(', ')'
    );

    ParallelLetterFrequency(String[] texts) {
        this.texts = texts;
        result = Arrays.stream(texts)
                        .parallel()
                        .map(this::countLettersInText)
                        .reduce(this::mergeMaps)
                        .orElseGet(HashMap::new);
    }

    Map<Character, Integer> countLetters() {
        return result;
    }

    private Map<Character, Integer> countLettersInText(String text) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));

            if (skipSet.contains(c))
                continue;

            if (c >= '0' && c <= '9')
                continue;

            map.merge(c, 1, Integer::sum);
        }

        return map;
    }

    private Map<Character, Integer> mergeMaps(
            Map<Character, Integer> left,
            Map<Character, Integer> right) {

        right.forEach((k, v) ->
                left.merge(k, v, Integer::sum));

        return left;
    }
}
