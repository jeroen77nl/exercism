class Atbash {

    String encode(String input) {
        return group(transform(normalize(input)), 5);
    }

    String decode(String input) {
        return transform(normalize(input));
    }

    private String normalize(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = Character.toLowerCase(input.charAt(i));
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            }
        }

        return result.toString();
    }

    private String transform(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            result.append(convert(input.charAt(i)));
        }

        return result.toString();
    }

    private String group(String input, int size) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (i > 0 && i % size == 0) {
                result.append(' ');
            }
            result.append(input.charAt(i));
        }

        return result.toString();
    }

    private char convert(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) ('z' - (c - 'a'));
        }
        return c;
    }
}