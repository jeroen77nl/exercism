import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Say {

    private static final String NEGATIVE_MSG =
            "numbers below zero are out of range";

    private static final Map<Long, String> words = new HashMap<>();

    static {
        words.put(0L, "zero");
        words.put(1L, "one");
        words.put(2L, "two");
        words.put(3L, "three");
        words.put(4L, "four");
        words.put(5L, "five");
        words.put(6L, "six");
        words.put(7L, "seven");
        words.put(8L, "eight");
        words.put(9L, "nine");
        words.put(10L, "ten");
        words.put(11L, "eleven");
        words.put(12L, "twelve");
        words.put(13L, "thirteen");
        words.put(14L, "fourteen");
        words.put(15L, "fifteen");
        words.put(16L, "sixteen");
        words.put(17L, "seventeen");
        words.put(18L, "eighteen");
        words.put(19L, "nineteen");
        words.put(20L, "twenty");
        words.put(30L, "thirty");
        words.put(40L, "forty");
        words.put(50L, "fifty");
        words.put(60L, "sixty");
        words.put(70L, "seventy");
        words.put(80L, "eighty");
        words.put(90L, "ninety");
        words.put(100L, "hundred");
        words.put(1_000L, "thousand");
        words.put(1_000_000L, "million");
        words.put(1_000_000_000L, "billion");
    }

    private StringBuilder result;

    public String say(long number) {
        result = new StringBuilder();

        if (number < 0 || number > 999_999_999_999L) {
            throw new IllegalArgumentException(NEGATIVE_MSG);
        }

        if (number == 0) {
            return "zero";
        }

        for (long range : List.of(1_000_000_000L, 1_000_000L, 1_000L, 1L)) {
            parse3digitBlock(number, range);
            number %= range;
        }

        return result.toString();
    }

    private void parse3digitBlock(long number, long range) {
        if (number < range) return;

        long part = number / range;
        long digit1 = part / 100;
        long digits23 = part % 100;
        long digit2 = (part % 100) / 10;
        long digit3 = part % 10;

        if (digit1 > 0) {
            smartAppend(words.get(digit1));
            smartAppend(words.get(100L));
        }

        if (digits23 >= 21) {
            smartAppend(words.get(digit2 * 10));
            if (digit3 > 0) { // prevent appending "zero"
                smartAppend("-");
                smartAppend(words.get(digit3));
            }
        } else if (digits23 > 0) {
            smartAppend(words.get(digits23));
        }

        if (range >= 100) {
            smartAppend(words.get(range));
        }
    }

    private void smartAppend(String w) {
        if (!result.isEmpty()) {
            if ("-".equals(w) ||
                    '-' == result.charAt(result.length() - 1)) {
            } else {
                result.append(" ");
            }
        }
        result.append(w);
    }
}
