class Bob {

    String hey(String input) {
        input = input.trim();

        if (input.isEmpty()) {
            return "Fine. Be that way!";
        }

        boolean question = isQuestion(input);
        boolean yell = isYelling(input);

        if (question && yell) {
            return "Calm down, I know what I'm doing!";
        } else if (question) {
            return "Sure.";
        } else if (yell) {
            return "Whoa, chill out!";
        } else {
            return "Whatever.";
        }
    }

    private static boolean isQuestion(String input) {
        return input.endsWith("?");
    }

    private boolean isYelling(String input) {
        boolean hasLetter = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                if (Character.isLowerCase(c)) {
                    return false;
                }
                hasLetter = true;
            }
        }

        return hasLetter;
    }
}