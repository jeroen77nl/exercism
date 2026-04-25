import java.util.ArrayList;
import java.util.List;

class BaseConverter {

    private final long base10Value;

    BaseConverter(int originalBase, int[] originalDigits) {
        if (originalBase < 2) {
            throw new IllegalArgumentException("Bases must be at least 2.");
        }

        int base10 = 0;
        for (int i = 0; i < originalDigits.length; i++) {
            int digit = originalDigits[i];
            checkDigit(originalBase, digit);
            base10 = (base10 * originalBase + digit);
        }
        base10Value = base10;
    }

    private static void checkDigit(int originalBase, int digit) {
        if (digit < 0) {
            throw new IllegalArgumentException("Digits may not be negative.");
        }
        if (digit >= originalBase) {
            throw new IllegalArgumentException("All digits must be strictly less than the base.");
        }
    }

    int[] convertToBase(int newBase) {
        if (newBase < 2) {
            throw new IllegalArgumentException("Bases must be at least 2.");
        }

        if (base10Value == 0) {
            return new int[]{0};
        }

        List<Integer> result = new ArrayList<>();

        long d = base10Value;
        while (d != 0) {
            long r = d % newBase;
            result.add((int) r);
            d = d / newBase;
        }

        return result.reversed().stream()
                .mapToInt(Integer::intValue)
                .toArray();

    }
}