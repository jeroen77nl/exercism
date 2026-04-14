class RotationalCipher {

    private final int shiftKey;

    RotationalCipher(int shiftKey) {
        this.shiftKey = shiftKey % 26;
    }

    String rotate(String clearText) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < clearText.length(); i++) {
            encryptedText.append(shiftChar(clearText.charAt(i)));
        }
        return encryptedText.toString();
    }

    private char shiftChar(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return shiftLetter(c);
        }
        return c;

    }

    private char shiftLetter(char originalChar) {
        int base = Character.isLowerCase(originalChar) ? 'a' : 'A';
        return (char) (base + (originalChar - base + shiftKey) % 26);
    }
}
