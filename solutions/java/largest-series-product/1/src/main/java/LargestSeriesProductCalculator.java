import java.util.Arrays;

class LargestSeriesProductCalculator {

    private final int[] digits;
    private final boolean digitsAllZero;

    LargestSeriesProductCalculator(String inputNumber) {
        if (!inputNumber.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("String to search may only contain digits.");
        }
        digits = inputNumber.chars()
                .map(c -> c - '0')
                .toArray();

        digitsAllZero = Arrays.stream(digits)
                .allMatch(digit -> digit == 0);
    }

    long calculateLargestProductForSeriesLength(int numberOfDigits) {

        validateSpanSize(numberOfDigits);

        if (digitsAllZero) {
            return 0;
        }

        long largestProduct = -1;
        for (int i = 0; i < digits.length - numberOfDigits + 1; i++) {
            long product = digits[i];
            for (int j = i + 1; j < i + numberOfDigits; j++) {
                product *= digits[j];
            }
            largestProduct = Math.max(product, largestProduct);
        }
        return largestProduct;
    }

    private void validateSpanSize(int numberOfDigits) {
        if (numberOfDigits > digits.length) {
            throw new IllegalArgumentException(
                    "Series length must be less than or equal to the length of the string to search.");
        }
        if (numberOfDigits < 0) {
            throw new IllegalArgumentException("Series length must be non-negative.");
        }
    }
}
