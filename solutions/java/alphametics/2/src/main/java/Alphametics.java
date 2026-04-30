import java.util.*;

class Alphametics {

    private final String sum;
    private final String[] terms;

    private final List<Character> letters = new ArrayList<>();
    private final Set<Character> leadingLetters = new HashSet<>();

    private final Map<Character, Integer> assignment = new HashMap<>();
    private final boolean[] used = new boolean[10];

    Alphametics(String input) {
        String[] parts = input.split(" == ");
        terms = parts[0].split(" \\+ ");
        sum = parts[1];

        Set<Character> unique = new HashSet<>();

        for (String word : terms) {
            for (int i = 0; i < word.length(); i++) {
                unique.add(word.charAt(i));
                if (i == 0) leadingLetters.add(word.charAt(i));
            }
        }

        for (int i = 0; i < sum.length(); i++) {
            unique.add(sum.charAt(i));
            if (i == 0) leadingLetters.add(sum.charAt(i));
        }

        letters.addAll(unique);
    }

    Map<Character, Integer> solve() throws UnsolvablePuzzleException {
        if (solve(0)) {
            return assignment;
        }
        throw new UnsolvablePuzzleException();
    }

    private boolean solve(int index) {
        if (index == letters.size()) {
            return isValid();
        }

        char letter = letters.get(index);

        for (int digit = 0; digit <= 9; digit++) {
            if (used[digit]) continue;
            if (digit == 0 && leadingLetters.contains(letter)) continue;

            assignment.put(letter, digit);
            used[digit] = true;

            if (solve(index + 1)) return true;

            used[digit] = false;
            assignment.remove(letter);
        }

        return false;
    }

    private boolean isValid() {
        int sumTerms = Arrays.stream(terms)
                .mapToInt(this::wordValue)
                .sum();

        return sumTerms == wordValue(sum);
    }

    private int wordValue(String word) {
        int result = 0;
        for (int i = 0; i < word.length(); i++) {
            result = result * 10 + assignment.get(word.charAt(i));
        }
        return result;
    }
}