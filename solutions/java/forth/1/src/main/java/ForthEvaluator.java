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
            } else if ("+".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalArgumentException("Addition requires that the stack contain at least 2 values");
                stack.add(stack.removeLast() + stack.removeLast());
            } else if ("-".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalArgumentException("Subtraction requires that the stack contain at least 2 values");
                int first = stack.removeLast();
                stack.add(stack.removeLast() - first);
            } else if ("*".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalArgumentException("Multiplication requires that the stack contain at least 2 values");
                stack.add(stack.removeLast() * stack.removeLast());
            } else if ("/".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalArgumentException("Division requires that the stack contain at least 2 values");
                int first = stack.removeLast();
                if (first == 0)
                    throw new IllegalArgumentException("Division by 0 is not allowed");
                stack.add(stack.removeLast() / first);
            } else if ("dup".equals(token)) {
                if (stack.isEmpty())
                    throw new IllegalArgumentException("Duplicating requires that the stack contain at least 1 value");
                stack.add(stack.peekLast());
            } else if ("drop".equals(token)) {
                if (stack.isEmpty())
                    throw new IllegalArgumentException("Dropping requires that the stack contain at least 1 value");
                stack.removeLast();
            } else if ("swap".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalArgumentException("Swapping requires that the stack contain at least 2 values");
                int first = stack.removeLast();
                int second = stack.removeLast();
                stack.add(first);
                stack.add(second);
            } else if ("over".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalArgumentException("Overing requires that the stack contain at least 2 values");
                int first = stack.removeLast();
                Integer second = stack.peekLast();
                stack.add(first);
                stack.add(second);
            } else {
                throw new IllegalArgumentException("No definition available for operator \"" + token + "\"");
            }
        }
    }

    private Optional<Integer> parseToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
