import java.util.*;

class Alphametics {

    private String sum;
    private String[] terms;

    Set<Character> inputValues = new HashSet<>();
    Map<Character, Integer> outputValues = new HashMap<>();
    Set<Character> beginLetters = new HashSet<>();

    Alphametics(String userInput) {
        parseInput(userInput);

        for (String word : terms)
            for (int i = 0; i < word.length(); i++) {
                inputValues.add(word.charAt(i));
                if (i == 0) {
                    beginLetters.add(word.charAt(i));
                }
            }

        for (int i = 0; i < sum.length(); i++) {
            inputValues.add(sum.charAt(i));
            if (i == 0) {
                beginLetters.add(sum.charAt(i));
            }
        }
    }

    Map<Character, Integer> solve() throws UnsolvablePuzzleException {
        Map<Character, Integer> result = solveIter();
        if (result == null) throw new UnsolvablePuzzleException();
        return result;
    }

    private Map<Character, Integer> solveIter() throws UnsolvablePuzzleException {
        if (inputValues.isEmpty()) {
            int sumOfTerms = Arrays.stream(terms).mapToInt(this::wordValue).sum();
            int sumOfInput = wordValue(sum);
            if (sumOfTerms == sumOfInput) {
                return outputValues;
            } else {
                return null;
            }
        }

        char letter = inputValues.stream().findFirst().orElseThrow(RuntimeException::new);
        outputValues.put(letter, -1);
        inputValues.remove(letter);
        int iStart = 0;
        if (beginLetters.contains(letter)) {
            iStart = 1;
        }
        for (int i = iStart; i <= 9; i++) {
            if (outputValues.containsValue(i)) continue;
            outputValues.put(letter, i);
            Map<Character, Integer> result = solveIter();
            if (result == null) continue;
            return result;
        }
        outputValues.remove(letter);
        inputValues.add(letter);
        return null;
    }

    private void parseInput(String userInput) {
        String[] termsAndSum = userInput.split(" == ");
        terms = termsAndSum[0].split(" \\+ ");
        sum = termsAndSum[1];
    }

    private int wordValue(String letters) {
        int factor = 1;
        int result = 0;
        for (int i = letters.length() - 1; i >= 0; i--) {
            int letterValue = outputValues.get(letters.charAt(i)) * factor;
            factor *= 10;
            result += letterValue;
        }
        return result;
    }

}