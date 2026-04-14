import java.text.BreakIterator;
import java.util.Locale;

class MicroBlog {
    public String truncate(String input) {
        return input.codePoints()
                .limit(5)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // this version can handle graphemes (characters spanning multiple codepoints) using Java BreakIterator)
    public String truncateGraphemes(String input) {
        BreakIterator it = BreakIterator.getCharacterInstance(Locale.ROOT);
        it.setText(input);

        StringBuilder firstFiveCharacters = new StringBuilder();
        int start = it.first();
        int end = it.next();
        int count = 1;
        while (end != BreakIterator.DONE && count <= 5) {
            firstFiveCharacters.append(input, start, end);
            start = end;
            end = it.next();
            count++;
        }

        return firstFiveCharacters.toString();
    }

    /* test with:
        String expected = "👍🏿👍🏿👍👍🏿👍🏿";
        assertThat(microBlog.truncateGraphemes("👍🏿👍🏿👍👍🏿👍🏿👍🏿👍🏿")).isEqualTo(expected);
     */
}
