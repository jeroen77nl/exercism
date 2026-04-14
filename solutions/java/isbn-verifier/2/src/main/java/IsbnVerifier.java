class IsbnVerifier {

    boolean isValid(String isbn) {

        isbn = normalize(isbn);

        if (!checkFormat(isbn)) {
            return false;
        }

        return checkContent(isbn);
    }

    private String normalize(String isbn) {
        return isbn.replace("-", "");
    }

    private boolean checkFormat(String isbn) {
        if (isbn.length() != 10) {
            return false;
        }

        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }

        char last = isbn.charAt(9);
        return Character.isDigit(last) || last == 'X';
    }

    private boolean checkContent(String isbn) {
        int sum = 0;

        for (int i = 0; i < isbn.length(); i++) {
            char c = isbn.charAt(i);
            int digit = (i == 9 && c == 'X') ? 10 : c - '0';
            int factor = 10 - i;
            sum += digit * factor;
        }

        return sum % 11 == 0;
    }
}
