import java.util.StringJoiner;

public class Say {

    private static final long MAX_NUMBER = 999_999_999_999L;

    private static final String NUMBER_NOT_ALLOWED_MSG =
            "number must be between 0 and 999999999999";

    private static final String[] SMALL_NUMBERS = {
            "zero", "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten", "eleven",
            "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static record Scale(long value, String name) {}

    private static final Scale[] SCALES = {
            new Scale(1_000_000_000L, "billion"),
            new Scale(1_000_000L, "million"),
            new Scale(1_000L, "thousand"),
            new Scale(1L, "")
    };

    public String say(long number) {
        if (number < 0 || number > MAX_NUMBER) {
            throw new IllegalArgumentException(NUMBER_NOT_ALLOWED_MSG);
        }

        if (number == 0) {
            return "zero";
        }

        StringJoiner result = new StringJoiner(" ");
        long remaining = number;

        for (Scale scale : SCALES) {
            int block = (int) (remaining / scale.value());

            if (block > 0) {
                String phrase = sayUnder1000(block);

                if (!scale.name().isEmpty()) {
                    phrase = phrase + " " + scale.name();
                }

                result.add(phrase);
            }

            remaining %= scale.value();
        }

        return result.toString();
    }

    private static String sayUnder1000(int number) {
        StringJoiner result = new StringJoiner(" ");

        int hundreds = number / 100;
        int remainder = number % 100;

        if (hundreds > 0) {
            result.add(SMALL_NUMBERS[hundreds] + " hundred");
        }

        if (remainder > 0) {
            result.add(sayUnder100(remainder));
        }

        return result.toString();
    }

    private static String sayUnder100(int number) {
        if (number < 20) {
            return SMALL_NUMBERS[number];
        }

        int tens = number / 10;
        int ones = number % 10;

        return ones == 0
                ? TENS[tens]
                : TENS[tens] + "-" + SMALL_NUMBERS[ones];
    }
}