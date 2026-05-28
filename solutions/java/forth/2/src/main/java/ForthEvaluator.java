import java.util.*;

class ForthEvaluator {
    Deque<Integer> stack = new ArrayDeque<>();
    Map<String, List<String>> symbols = new HashMap<>();

    List<Integer> evaluateProgram(List<String> input) {

        for (String subList : input) {

            String[] tokens = subList.split(" ");

            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].toLowerCase();
            }

            if (":".equals(tokens[0]) && ";".equals(tokens[tokens.length - 1])) {
                processSymbolDefinition(tokens);
            } else {
                processNormalTokens(tokens);
            }

        }

        return stack.stream().toList();
    }

    private void processSymbolDefinition(String[] tokens) {
        String symbolName = tokens[1];

        if (parseToInt(symbolName).isPresent())
            throw new IllegalArgumentException("Cannot redefine numbers");

        List<String> symbolValues = new ArrayList<>();
        for (int i = 2; i < tokens.length - 1; i++) {
            String symbolValue = tokens[i];
            if (symbols.containsKey(symbolValue)) {
                symbolValues.addAll(symbols.get(symbolValue));
            } else {
                symbolValues.add(symbolValue);
            }
        }

        symbols.put(symbolName, symbolValues);
    }

    private void processNormalTokens(String[] tokens) {
        List<String> tokensWithSymbols = new ArrayList<>();
        for (String token : tokens) {
            if (symbols.containsKey(token)) {
                tokensWithSymbols.addAll(symbols.get(token));
            } else {
                tokensWithSymbols.add(token);
            }
        }

        for (String token : tokensWithSymbols) {
            Optional<Integer> optionalInteger = parseToInt(token);
            if (optionalInteger.isPresent()) {
                stack.add(optionalInteger.get());
            } else if (Set.of("+", "-", "*", "/", "swap", "over").contains(token)) {
                processBinaryOperator(token);
            } else if (Set.of("drop", "dup").contains(token)) {
                processUnaryOperator(token);
            } else {
                throw new IllegalArgumentException("No definition available for operator \"" + token + "\"");
            }
        }
    }

    private void processUnaryOperator(String token) {
        checkStackSizeForUnaryOperator(token);

        if ("drop".equals(token)) {
            stack.removeLast();
        } else if ("dup".equals(token)) {
            stack.add(stack.peekLast());
        }
    }
    private void checkStackSizeForUnaryOperator(String token) {
        Map<String, String> tokenDescriptions = Map.of(
                "drop", "Dropping",
                "dup", "Duplicating"
        );

        if (!stack.isEmpty()) return;

        String message =
                "%s requires that the stack contain at least 1 value"
                        .formatted(tokenDescriptions.get(token));
        throw new IllegalArgumentException(message);
    }

    private void processBinaryOperator(String token) {
        checkStackSizeForBinaryOperator(token);
        int fst = stack.removeLast();
        int snd = stack.removeLast();

        if ("+".equals(token)) {
            stack.add(fst + snd);
        } else if ("-".equals(token)) {
            stack.add(snd - fst);
        } else if ("*".equals(token)) {
            stack.add(fst * snd);
        } else if ("/".equals(token)) {
            if (fst == 0)
                throw new IllegalArgumentException("Division by 0 is not allowed");
            stack.add(snd / fst);
        } else if ("swap".equals(token)) {
            stack.add(fst);
            stack.add(snd);
        } else if ("over".equals(token)) {
            stack.add(snd);
            stack.add(fst);
            stack.add(snd);
        }
    }

    private void checkStackSizeForBinaryOperator(String token) {
        Map<String, String> tokenDescriptions = Map.of(
                "+", "Addition",
                "-", "Subtraction",
                "*", "Multiplication",
                "/", "Division",
                "swap", "Swapping",
                "over", "Overing"
        );

        if (stack.size() >= 2) return;

        String message =
                "%s requires that the stack contain at least 2 values"
                        .formatted(tokenDescriptions.get(token));
        throw new IllegalArgumentException(message);
    }

    private Optional<Integer> parseToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
