class Acronym {

    private final String result;

    Acronym(String phrase) {
        result = acronymOf(phrase);
    }

    String get() {
        return result;
    }

    private String acronymOf(String phrase) {
        StringBuilder acronym = new StringBuilder();
        boolean startOfWord = true;

        for (char c : phrase.toCharArray()) {
            if (Character.isLetter(c) && startOfWord) {
                acronym.append(Character.toUpperCase(c));
                startOfWord = false;
            } else if (c == ' ' || c == '-') {
                startOfWord = true;
            }
        }

        return acronym.toString();
    }
}