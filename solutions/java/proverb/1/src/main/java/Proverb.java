class Proverb {
    private static final String TEMPLATE_TEXT =
            "For want of a %s the %s was lost.\n";
    private static final String TEMPLATE_TEXT_END =
            "And all for the want of a %s.";

    private final String[] words;

    Proverb(String[] words) {
        this.words = words.clone();
    }

    String recite() {

        StringBuilder result = new StringBuilder();

        if (words.length == 0) {
            return "";
        }

        for (int i = 0; i < words.length - 1; i++) {
            String current = words[i];
            String next = words[i + 1];
            result.append(TEMPLATE_TEXT.formatted(current, next));
        }
        result.append(TEMPLATE_TEXT_END.formatted(words[0]));

        return  result.toString();
    }

}
