class PhoneNumber {

    private static final int AREA_CODE_INDEX = 0;
    private static final int EXCHANGE_CODE_INDEX = 3;

    private final String number;

    PhoneNumber(String numberString) {
        StringBuilder digits = new StringBuilder();

        parseInput(numberString, digits);
        normalizeLength(digits);
        validateContent(digits);

        number = digits.toString();
    }

    private static void validateContent(StringBuilder digits) {
        char area = digits.charAt(AREA_CODE_INDEX);
        if (area == '0') {
            throw new IllegalArgumentException("area code cannot start with zero");
        } else if (area == '1') {
            throw new IllegalArgumentException("area code cannot start with one");
        }

        char exchange = digits.charAt(EXCHANGE_CODE_INDEX);
        if (exchange == '0') {
            throw new IllegalArgumentException("exchange code cannot start with zero");
        } else if (exchange == '1') {
            throw new IllegalArgumentException("exchange code cannot start with one");
        }
    }

    private static void normalizeLength(StringBuilder digits) {

        if (digits.length() < 10) {
            throw new IllegalArgumentException("must not be fewer than 10 digits");
        } else if (digits.length() > 11) {
            throw new IllegalArgumentException("must not be greater than 11 digits");
        } else if (digits.length() == 11) {
            if (digits.charAt(0) != '1') {
                throw new IllegalArgumentException("11 digits must start with 1");
            } else {
                digits.deleteCharAt(0);
            }
        }

        if (digits.length() != 10) {
            throw new IllegalArgumentException("incorrect number of digits");
        }
    }

    private static void parseInput(String numberString, StringBuilder digits) {
        for (int i = 0; i < numberString.length(); i++) {
            char c = numberString.charAt(i);
            if (Character.isDigit(c)) {
                digits.append(c);
            } else if (Character.isLetter(c)) {
                throw new IllegalArgumentException("letters not permitted");
            } else if ("().-+ ".indexOf(c) == -1) {
                throw new IllegalArgumentException("punctuations not permitted");
            }
        }
    }

    String getNumber() {
        return number;
    }

}