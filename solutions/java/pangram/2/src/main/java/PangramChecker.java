public class PangramChecker {

    public boolean isPangram(String input) {
        return input.toLowerCase()
                .codePoints()
                .filter(Character::isLetter)
                .distinct()
                .count() == 26;
    }
}
