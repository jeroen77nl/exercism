class LuhnValidator {

    boolean isValid(String candidate) {

        String input = candidate.replace(" ", "");

        if (input.length() <= 1) {
            return false;
        }

        if (!input.chars().allMatch(Character::isDigit)) {
            return false;
        }

        int[] idArray = new StringBuilder(input)
                .reverse()
                .toString()
                .chars()
                .map(c -> c - '0')
                .toArray();

        int sum = 0;
        for (int i = 0; i <= idArray.length - 1; i++) {
            if (i % 2 == 1) {
                idArray[i] *= 2;
                if (idArray[i] > 9) {
                    idArray[i] -= 9;
                }
            }
            sum += idArray[i];
        }
        return sum % 10 == 0;
    }
}
