import java.util.HashMap;
import java.util.Map;

class WordCount {

    public Map<String, Integer> phrase(String input) {
        Map<String, Integer> counts = new HashMap<>();

        String[] words = normalizeInput(input).split(" ");

        for (String word : words) {
            if (word.isEmpty() || word.equals(" "))
                continue;
            word = trimSingleQuotes(word);
            counts.put(word, counts.computeIfAbsent(word, _ -> 0) + 1);
        }

        return counts;
    }

    private String normalizeInput(String input) {
        final String WORD_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789'";
        final String DELIMITERS = " \n:,";

        input = input.toLowerCase();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (WORD_CHARACTERS.indexOf(c) >= 0) {
                builder.append(c);
            } else if (DELIMITERS.indexOf(c) >= 0)
                builder.append(' ');
        }

        return builder.toString();
    }

    private static String trimSingleQuotes(String word) {
        if (word.startsWith("'")) word = word.substring(1);
        if (word.endsWith("'")) word = word.substring(0, word.length() - 1);
        return word;
    }
}
