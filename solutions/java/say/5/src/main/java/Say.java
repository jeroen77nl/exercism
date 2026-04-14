import java.util.Map;
import java.util.StringJoiner;

public class Say {

    private static final String NUMBER_OUT_OF_RANGE_MSG =
            "number must be between 0 and 999999999999";

    private static final Map<Long, String> WORDS = Map.ofEntries(
            Map.entry(0L, "zero"),
            Map.entry(1L, "one"),
            Map.entry(2L, "two"),
            Map.entry(3L, "three"),
            Map.entry(4L, "four"),
            Map.entry(5L, "five"),
            Map.entry(6L, "six"),
            Map.entry(7L, "seven"),
            Map.entry(8L, "eight"),
            Map.entry(9L, "nine"),
            Map.entry(10L, "ten"),
            Map.entry(11L, "eleven"),
            Map.entry(12L, "twelve"),
            Map.entry(13L, "thirteen"),
            Map.entry(14L, "fourteen"),
            Map.entry(15L, "fifteen"),
            Map.entry(16L, "sixteen"),
            Map.entry(17L, "seventeen"),
            Map.entry(18L, "eighteen"),
            Map.entry(19L, "nineteen"),
            Map.entry(20L, "twenty"),
            Map.entry(30L, "thirty"),
            Map.entry(40L, "forty"),
            Map.entry(50L, "fifty"),
            Map.entry(60L, "sixty"),
            Map.entry(70L, "seventy"),
            Map.entry(80L, "eighty"),
            Map.entry(90L, "ninety")
    );

    private static final long[] SCALE_NUMBERS = {
            1_000_000_000L,
            1_000_000L,
            1_000L,
            1L
    };

    private static final String[] SCALE_WORDS = {
            "billion",
            "million",
            "thousand",
            ""
    };

    public String say(long number) {
        if (number < 0 || number > 999_999_999_999L) {
            throw new IllegalArgumentException(NUMBER_OUT_OF_RANGE_MSG);
        }

        StringJoiner result = new StringJoiner(" ");
        long remaining = number;

        for (int i = 0; i < SCALE_NUMBERS.length; i++) {
            long range = SCALE_NUMBERS[i];
            long block = remaining / range;

            if (block > 0) {
                String chunk = sayUnder1000(block);
                String scale = SCALE_WORDS[i];

                result.add(scale.isEmpty() ? chunk : chunk + " " + scale);
            }

            remaining %= range;
        }

        String spoken = result.toString();
        return spoken.isEmpty() ? "zero" : spoken;
    }

    private static String sayUnder1000(long number) {
        StringJoiner result = new StringJoiner(" ");

        long hundreds = number / 100;
        long remainder = number % 100;

        if (hundreds > 0) {
            result.add(WORDS.get(hundreds) + " hundred");
        }

        if (remainder > 0) {
            result.add(sayUnder100(remainder));
        }

        return result.toString();
    }

    private static String sayUnder100(long number) {
        if (number <= 20) {
            return WORDS.get(number);
        }

        long tens = (number / 10) * 10;
        long ones = number % 10;

        return ones == 0
                ? WORDS.get(tens)
                : WORDS.get(tens) + "-" + WORDS.get(ones);
    }
}