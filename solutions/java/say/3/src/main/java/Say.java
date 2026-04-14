import java.util.Map;

public class Say {

    private static final String NUMBER_NOT_ALLOWED_MSG =
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
            Map.entry(90L, "ninety"),
            Map.entry(100L, "hundred"),
            Map.entry(1_000L, "thousand"),
            Map.entry(1_000_000L, "million"),
            Map.entry(1_000_000_000L, "billion")
    );

    private static final long[] RANGES = {
            1_000_000_000L,
            1_000_000L,
            1_000L,
            1L
    };

    public String say(long number) {
        if (number < 0 || number > 999_999_999_999L) {
            throw new IllegalArgumentException(NUMBER_NOT_ALLOWED_MSG);
        }

        StringBuilder result = new StringBuilder();
        long remaining = number;

        for (long range : RANGES) {
            parse3digitBlock(remaining, range, result);
            remaining %= range;
        }

        return result.isEmpty() ? "zero" : result.toString();    }

    private static void parse3digitBlock(long number, long range, StringBuilder result) {
        if (number < range) return;

        long block = number / range;
        long hundreds = block / 100;
        long remainder = block % 100;
        long tens = remainder / 10;
        long ones = remainder % 10;

        if (hundreds > 0) {
            smartAppend(result, WORDS.get(hundreds));
            smartAppend(result, WORDS.get(100L));
        }

        if (remainder >= 21) {
            smartAppend(result, WORDS.get(tens * 10));
            if (ones > 0) { // prevent appending "zero"
                smartAppend(result, "-");
                smartAppend(result, WORDS.get(ones));
            }
        } else if (remainder > 0) {
            smartAppend(result, WORDS.get(remainder));
        }

        if (range >= 1_000L) {
            smartAppend(result, WORDS.get(range));
        }
    }

    private static void smartAppend(StringBuilder result, String w) {
        if (!result.isEmpty()
                && !"-".equals(w)
                && result.charAt(result.length() - 1) != '-') {
            result.append(" ");
        }
        result.append(w);
    }
}
