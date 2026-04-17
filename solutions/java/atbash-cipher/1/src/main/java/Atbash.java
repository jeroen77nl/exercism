class Atbash {

    String encode(String input) {
        StringBuilder result = new StringBuilder();
        int countOutput = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            c = Character.toLowerCase(c);
            if (Character.isLetter(c) || Character.isDigit(c)) {
                countOutput++;
                result.append(convert(c));
                if (countOutput % 5 == 0) {
                    result.append(' ');
                }
            }
        }
        return result.toString().trim();
    }

    String decode(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ') {
                result.append(convert(c));
            }
        }
        return result.toString().trim();
    }

    private char convert(char c) {
        if (c >= 'a' && c <= 'z') {
            int i = (25 - (c - 'a')) + 'a';
            return (char) i;
        }
        return c;
    }
}
