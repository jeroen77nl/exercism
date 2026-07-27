import java.util.*;
import java.util.function.IntBinaryOperator;

enum Operation {
    PLUS(Integer::sum),
    MINUS((a, b) -> a - b),
    MULTIPLY((a, b) -> a * b),
    DIVIDE((a, b) -> a / b);

    private final IntBinaryOperator operator;

    Operation(IntBinaryOperator operator) {
        this.operator = operator;
    }

    int apply(int a, int b) {
        return operator.applyAsInt(a, b);
    }
}

record ParsedExpression(
        int first,
        List<Step> operations
) {
}

record Step(
        Operation operation,
        int value
) {
}

class WordProblemSolver {

    private static final Map<String, Operation> OPERATIONS_BY_SYMBOL =
            Map.of(
                    "+", Operation.PLUS,
                    "-", Operation.MINUS,
                    "*", Operation.MULTIPLY,
                    "/", Operation.DIVIDE
            );

    private static final String MESSAGE = "I'm sorry, I don't understand the question!";

    int solve(final String wordProblem) {

        String normalizedInput = normalize(wordProblem);
        String[] tokens = normalizedInput.split(" ");

        ParsedExpression parsedExpression = parseExpression(tokens);

        int result = parsedExpression.first();

        for (Step step : parsedExpression.operations()) {
            result = step.operation().apply(result, step.value());
        }

        return result;
    }

    private String normalize(String wordProblem) {
        if (!wordProblem.startsWith("What is ") || !wordProblem.endsWith("?")) {
            throw new IllegalArgumentException(MESSAGE);
        }

        String body = wordProblem.substring(
                "What is ".length(), // trim "What is "
                wordProblem.length() - 1); // trim last "?"

        return body
                .replace("plus", "+")
                .replace("minus", "-")
                .replace("divided by", "/")
                .replace("multiplied by", "*");
    }

    private ParsedExpression parseExpression(String[] tokens) {

        if (tokens.length % 2 == 0)
            throw new IllegalArgumentException(MESSAGE);

        Integer first = parseIntOrNull(tokens[0]);
        if (first == null) {
            throw new IllegalArgumentException(MESSAGE);
        }

        List<Step> stepList = new ArrayList<>();

        for (int i = 1; i < tokens.length; i += 2) {
            Operation operation = OPERATIONS_BY_SYMBOL.get(tokens[i]);
            if (operation == null) {
                throw new IllegalArgumentException(MESSAGE);
            }

            Integer number = parseIntOrNull(tokens[i + 1]);
            if (number == null) {
                throw new IllegalArgumentException(MESSAGE);
            }

            stepList.add(new Step(operation, number));
        }
        return new ParsedExpression(first, List.copyOf(stepList));
    }

    private static Integer parseIntOrNull(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
