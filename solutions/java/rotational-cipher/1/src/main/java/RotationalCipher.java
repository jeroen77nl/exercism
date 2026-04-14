class RotationalCipher {

    private final int shiftKey;

    RotationalCipher(int shiftKey) {
        this.shiftKey = shiftKey;
    }

    String rotate(String clearText) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < clearText.length(); i++) {
            int originalChar = clearText.charAt(i);
            int shiftedChar = shiftChar(originalChar);
            encryptedText.append((char) shiftedChar);
        }
        return encryptedText.toString();
    }

    private int shiftChar(int originalChar) {
        if (Character.isAlphabetic(originalChar)) {
            return shiftLetter(originalChar);
        } else {
            return originalChar;
        }
    }

    private int shiftLetter(int originalChar) {
        int shiftedChar;
        shiftedChar = originalChar + shiftKey;
        if (Character.isLowerCase(originalChar) && shiftedChar > 'z') {
            shiftedChar -= 26;
        } else if (Character.isUpperCase(originalChar) && shiftedChar > 'Z') {
            shiftedChar -= 26;
        }
        return shiftedChar;
    }
}
