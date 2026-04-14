public class PangramChecker {

    public boolean isPangram(String input) {
        return input.codePoints()
                .filter(Character::isLetter)
                .map(Character::toUpperCase)
                .distinct()
                .count() == 26;
    }

}
