import java.util.HashSet;
import java.util.Set;

class IsogramChecker {

    boolean isIsogram(String phrase) {
        Set<Character> letters = new HashSet<>();

        for (int i = 0; i < phrase.length(); i++) {
            char c = phrase.charAt(i);

            if (!Character.isLetter(c)) {
                continue;
            }

            char normalized = Character.toLowerCase(c);
            boolean added = letters.add(normalized);
            if (!added) {
                return false;
            }
        }
        return true;
    }
}
