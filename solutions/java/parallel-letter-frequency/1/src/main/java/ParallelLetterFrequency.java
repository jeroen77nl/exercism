import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ParallelLetterFrequency {

    private final String[] texts;

    Set<Character> skipSet = Set.of(
            ' ', '\t', '\n', '\r',
            '!', '?', ';', ',', '.', '-', ':', '"', '\'', '(', ')'
    );

    ParallelLetterFrequency(String[] texts) {
        this.texts = texts;
    }

    Map<Character, Integer> countLetters() {
        Map<Character, Integer> map = new HashMap<>();
        Arrays.stream(texts).forEach(text -> {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (skipSet.contains(c)) continue;
                if (c >= '0' && c <= '9') continue;
                c = Character.toLowerCase(c);
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        });
        return map;
    }

}
