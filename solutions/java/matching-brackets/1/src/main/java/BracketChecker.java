import java.util.*;

class BracketChecker {

    private final Set<Character> OPENING = Set.of('{', '[', '(');
    private final Set<Character> CLOSING = Set.of('}', ']', ')');
    private static final Map<Character, Character> MATCHES = Map.of(
            '}', '{',
            ']', '[',
            ')', '('
    );

    private final String expression;

    BracketChecker(String expression) {
        this.expression = expression;
    }

    boolean areBracketsMatchedAndNestedCorrectly() {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); i++) {
            char expressionChar = expression.charAt(i);
            if (OPENING.contains(expressionChar)) {
                stack.push(expressionChar);
            } else if (CLOSING.contains(expressionChar)) {
                if (stack.isEmpty() || stack.pop() != MATCHES.get(expressionChar)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}