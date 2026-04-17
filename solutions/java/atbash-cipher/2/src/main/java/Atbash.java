class Atbash {

    String encode(String input) {
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = Character.toLowerCase(input.charAt(i));

            if (Character.isLetterOrDigit(c)) {
                if (count > 0 && count % 5 == 0) {
                    result.append(' ');
                }
                result.append(convert(c));
                count++;
            }
        }

        return result.toString();
    }

    String decode(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                result.append(convert(Character.toLowerCase(c)));
            }
        }
        return result.toString().trim();
    }

    private char convert(char c) {
        c = Character.toLowerCase(c);
        if (c >= 'a' && c <= 'z') {
            return (char) ('z' - (c - 'a'));
        }
        return c;
    }
}
