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
            if (MATCHES.containsKey(c)) {  // keys are opening chars
                if (stack.isEmpty() || stack.pop() != MATCHES.get(c)) {
                    return false;
                }
            } else if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}