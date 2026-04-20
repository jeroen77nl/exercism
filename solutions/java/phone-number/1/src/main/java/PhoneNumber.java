class PhoneNumber {

    private final StringBuilder number = new StringBuilder();

    PhoneNumber(String numberString) {
        for (int i = 0; i < numberString.length(); i++) {
            char c = numberString.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else if (Character.isLetter(c)) {
                throw new IllegalArgumentException("letters not permitted");
            } else if ("().-+ ".contains(String.valueOf(c))) {
                continue;
            } else {
                throw new IllegalArgumentException("punctuations not permitted");
            }

        }

        if (number.length() < 10) {
            throw new IllegalArgumentException("must not be fewer than 10 digits");
        }

        if (number.length() > 11) {
            throw new IllegalArgumentException("must not be greater than 11 digits");
        }

        if (number.length() == 11) {
            if (number.charAt(0) == '1') {
                number.deleteCharAt(0);
            } else {
                throw new IllegalArgumentException("11 digits must start with 1");
            }
        }

        if (number.charAt(0) == '0') {
            throw new IllegalArgumentException("area code cannot start with zero");
        } else if (number.charAt(0) == '1') {
            throw new IllegalArgumentException("area code cannot start with one");
        }

        if (number.charAt(3) == '0') {
            throw new IllegalArgumentException("exchange code cannot start with zero");
        } else if (number.charAt(3) == '1') {
            throw new IllegalArgumentException("exchange code cannot start with one");
        }
    }

    String getNumber() {
        return number.toString();
    }

}