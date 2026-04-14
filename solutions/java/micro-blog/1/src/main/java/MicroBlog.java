import java.text.BreakIterator;
import java.util.Locale;

class MicroBlog {
    public String truncate(String input) {
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
}
