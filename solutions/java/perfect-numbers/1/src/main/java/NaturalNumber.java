import java.util.stream.IntStream;

class NaturalNumber {
    private static final String MSG_NON_NEGATIVE =
            "You must supply a natural number (positive integer)";

    private final int number;

    NaturalNumber(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException(MSG_NON_NEGATIVE);
        }
        this.number = number;
    }

    Classification getClassification() {
        return classify(calculateAliquotSum(number));
    }

    private Classification classify(int aliquotSum) {
        if (aliquotSum == number) return Classification.PERFECT;
        if (aliquotSum > number) return Classification.ABUNDANT;
        return Classification.DEFICIENT;
    }

    private int calculateAliquotSum(int n) {
        return IntStream.range(1, n)
                .filter(i -> n % i == 0)
                .sum();
    }
}
