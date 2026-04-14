class LuhnValidator {

    boolean isValid(String candidate) {

        String input = candidate.replace(" ", "");

        if (input.length() <= 1) return false;
        if (!input.chars().allMatch(Character::isDigit)) return false;

        int sum = 0;
        boolean doubleDigit = false;

        for (int i = input.length() - 1; i >= 0; i--) {
            int digit = input.charAt(i) - '0';

            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }

            sum += digit;
            doubleDigit = !doubleDigit;
        }

        return sum % 10 == 0;
    }
}