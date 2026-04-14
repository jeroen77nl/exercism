import java.util.Arrays;

class LargestSeriesProductCalculator {

    private final int[] digits;

    LargestSeriesProductCalculator(String inputNumber) {
        if (!inputNumber.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("String to search may only contain digits.");
        }
        digits = inputNumber.chars()
                .map(c -> c - '0')
                .toArray();
    }

    long calculateLargestProductForSeriesLength(int numberOfDigits) {

        validateSpanSize(numberOfDigits);

        long largestProduct = 0;
        for (int i = 0; i <= digits.length - numberOfDigits; i++) {
            long product = 1;
            for (int j = i; j < i + numberOfDigits; j++) {
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
        if (numberOfDigits == 0) {
            throw new IllegalArgumentException("Series length must be positive.");
        }
    }
}
