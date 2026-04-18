import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

class BracketChecker {

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

        for (char c : expression.toCharArray()) {
            Character expected = MATCHES.get(c);
            if (expected != null) {
                if (stack.isEmpty() || stack.pop() != expected) {
                    return false;
                }
            } else if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}