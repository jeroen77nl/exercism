import java.util.*;
import java.util.stream.Collectors;

enum Builtin {
    PLUS("+", 2, "Addition"),
    MINUS("-", 2, "Subtraction"),
    MUL("*", 2, "Multiplication"),
    DIV("/", 2, "Division"),
    DUP("dup", 1, "Duplicating"),
    DROP("drop", 1, "Dropping"),
    OVER("over", 2, "Overing"),
    SWAP("swap", 2, "Swapping");

    final String word;
    final int requiredStackSize;
    final String description;

    Builtin(String word, int requiredStackSize, String description) {
        this.word = word;
        this.requiredStackSize = requiredStackSize;
        this.description = description;
    }

    static final Map<String, Builtin> LOOKUP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(b -> b.word, b -> b));

    static Builtin from(String token) {
        return LOOKUP.get(token);
    }
}

class ForthEvaluator {
    Deque<Integer> stack = new ArrayDeque<>();
    Map<String, List<String>> symbols = new HashMap<>();

    List<Integer> evaluateProgram(List<String> input) {

        for (String subList : input) {

            String[] tokens = subList.split("\\s+");

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
                stack.addLast(optionalInteger.get());
                continue;
            }

            Builtin instruction = Builtin.LOOKUP.getOrDefault(token, null);

            if (instruction == null)
                throw new IllegalArgumentException("No definition available for operator \"" + token + "\"");

            if (instruction.requiredStackSize == 2) {
                processBinaryOperator(instruction);
            } else {
                processUnaryOperator(instruction);
            }
        }
    }

    private void processUnaryOperator(Builtin instruction) {
        checkStackSizeForUnaryOperator(instruction);

        if (instruction == Builtin.DROP) {
            stack.removeLast();
        } else if (instruction == Builtin.DUP) {
            stack.addLast(stack.peekLast());
        }
    }

    private void checkStackSizeForUnaryOperator(Builtin instruction) {
        if (!stack.isEmpty()) return;

        String message =
                "%s requires that the stack contain at least 1 value"
                        .formatted(instruction.description);
        throw new IllegalArgumentException(message);
    }

    private void processBinaryOperator(Builtin instruction) {
        checkStackSizeForBinaryOperator(instruction);
        int fst = stack.removeLast();
        int snd = stack.removeLast();

        if (Builtin.PLUS == instruction) {
            stack.addLast(fst + snd);
        } else if (Builtin.MINUS == instruction) {
            stack.addLast(snd - fst);
        } else if (Builtin.MUL == instruction) {
            stack.addLast(fst * snd);
        } else if (Builtin.DIV == instruction) {
            if (fst == 0)
                throw new IllegalArgumentException("Division by 0 is not allowed");
            stack.addLast(snd / fst);
        } else if (Builtin.SWAP == instruction) {
            stack.addLast(fst);
            stack.addLast(snd);
        } else if (Builtin.OVER == instruction) {
            stack.addLast(snd);
            stack.addLast(fst);
            stack.addLast(snd);
        }
    }

    private void checkStackSizeForBinaryOperator(Builtin instruction) {
        if (stack.size() >= instruction.requiredStackSize) return;

        String message =
                "%s requires that the stack contain at least 2 values"
                        .formatted(instruction.description);
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