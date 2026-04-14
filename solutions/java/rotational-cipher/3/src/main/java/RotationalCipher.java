import java.util.stream.Collectors;

class RotationalCipher {

    private final int shiftKey;

    RotationalCipher(int shiftKey) {
        this.shiftKey = shiftKey % 26;
    }

    String rotate(String clearText) {
        return clearText.chars()
                .mapToObj(c -> (char) c)
                .map(this::shiftChar)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private char shiftChar(char c) {
        if (isAsciiLetter(c)) {
            return shiftLetter(c);
        }
        return c;
    }

    private char shiftLetter(char originalChar) {
        int base = Character.isLowerCase(originalChar) ? 'a' : 'A';
        return (char) (base + (originalChar - base + shiftKey) % 26);
    }

    private boolean isAsciiLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}
