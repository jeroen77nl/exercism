import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Anagram {
    private final String word;
    private final Map<Character, Integer> wordLetterCount;

    public Anagram(String word) {
        this.word = word.toLowerCase();
        this.wordLetterCount = countLetters(this.word);
    }

    public List<String> match(List<String> candidates) {
        return candidates.stream()
                .filter(this::isAnagram)
                .toList();
    }

    private boolean isAnagram(String candidate) {
        candidate = candidate.toLowerCase();

        if (candidate.equals(this.word)) {
            return false;
        }

        if (candidate.length() != this.word.length()) {
            return false;
        }

        Map<Character,Integer> candidateLetterCount = countLetters(candidate);

        return this.wordLetterCount.equals(candidateLetterCount);
    }

    private static Map<Character,Integer> countLetters(String word) {
        Map<Character,Integer> counts = new HashMap<>();

        for (char c : word.toCharArray()) {
            counts.merge(c, 1, Integer::sum);
        }

        return counts;
    }
}