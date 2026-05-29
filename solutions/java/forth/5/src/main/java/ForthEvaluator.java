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
                tokens[i] = tokens[i].toLowerCase(Locale.ROOT);
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

            Builtin instruction = Builtin.from(token);

            if (instruction == null)
                throw new IllegalArgumentException("No definition available for operator \"" + token + "\"");

            execute(instruction);
        }
    }

    private void execute(Builtin instruction) {
        checkStackSize(instruction);

        switch (instruction) {
            case Builtin.PLUS -> {
                var p = pop2();
                push(p.a() + p.b());
            }
            case Builtin.MINUS -> {
                var p = pop2();
                push(p.a() - p.b());
            }
            case Builtin.MUL -> {
                var p = pop2();
                push(p.a() * p.b());
            }
            case Builtin.DIV -> {
                var p = pop2();
                if (p.b() == 0)
                    throw new IllegalArgumentException("Division by 0 is not allowed");
                push(p.a() / p.b());
            }
            case Builtin.SWAP -> {
                var p = pop2();
                push(p.b());
                push(p.a());
            }
            case Builtin.OVER -> {
                var p = pop2();
                push(p.a());
                push(p.b());
                push(p.a());
            }
            case Builtin.DROP -> pop();
            case Builtin.DUP -> push(peek());
        }
    }

    private void checkStackSize(Builtin instruction) {
        if (stack.size() >= instruction.requiredStackSize) return;

        String message =
                "%s requires that the stack contain at least %d value%s"
                        .formatted(
                                instruction.description,
                                instruction.requiredStackSize,
                                instruction.requiredStackSize == 1 ? "" : "s");
        throw new IllegalArgumentException(message);
    }

    private Optional<Integer> parseToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private int pop() {
        return stack.removeLast();
    }

    private void push(int value) {
        stack.addLast(value);
    }

    private int peek() {
        return stack.peekLast();
    }

    record IntPair(int a, int b) {
    }

    private IntPair pop2() {
        int b = pop();
        int a = pop();
        return new IntPair(a, b);
    }
}