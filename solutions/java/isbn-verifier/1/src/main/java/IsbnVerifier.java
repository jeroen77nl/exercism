class IsbnVerifier {

    boolean isValid(String stringToVerify) {

        stringToVerify = normalize(stringToVerify);

        boolean isFormatOk = checkFormat(stringToVerify);
        if (!isFormatOk) {
            return false;
        }

        return checkContent(stringToVerify);
    }

    private String normalize(String stringToVerify) {
        return stringToVerify.replace("-", "");
    }

    private boolean checkFormat(String stringToVerify) {
        if (stringToVerify.length() != 10) {
            return false;
        }

        char last = stringToVerify.charAt(9);
        if (! ((Character.isDigit(last)) || last == 'X')) {
            return false;
        }

        try {
            Long.parseLong(stringToVerify.substring(0, 9));
        } catch(NumberFormatException _) {
            return false;
        }

        return true;
    }

    private boolean checkContent(String stringToVerify) {
        int sum = 0;

        for (int i = 0; i < stringToVerify.length(); i++) {
            int charFactor;
            if (i == 9 && stringToVerify.charAt(i) == 'X') {
                charFactor = 10;
            } else {
                charFactor = stringToVerify.charAt(i) - '0';
            }
            int posFactor = 10 - i;
            sum += charFactor * posFactor;
        }

        return sum % 11 == 0;
    }
}
