import java.util.Collections;
import java.util.Map;

class TwelveDays {

    record Content(String number, String text) {
    }

    private final Map<Integer, Content> verses = Map.ofEntries(
            Map.entry(1, new Content("first", "Partridge in a Pear Tree")),
            Map.entry(2, new Content("second", "two Turtle Doves")),
            Map.entry(3, new Content("third", "three French Hens")),
            Map.entry(4, new Content("fourth", "four Calling Birds")),
            Map.entry(5, new Content("fifth", "five Gold Rings")),
            Map.entry(6, new Content("sixth", "six Geese-a-Laying")),
            Map.entry(7, new Content("seventh", "seven Swans-a-Swimming")),
            Map.entry(8, new Content("eighth", "eight Maids-a-Milking")),
            Map.entry(9, new Content("ninth", "nine Ladies Dancing")),
            Map.entry(10, new Content("tenth", "ten Lords-a-Leaping")),
            Map.entry(11, new Content("eleventh", "eleven Pipers Piping")),
            Map.entry(12, new Content("twelfth", "twelve Drummers Drumming"))
    );

    String verse(int verseNumber) {
        String mainText = "On the %s day of Christmas my true love gave to me: %s.\n";
        StringBuilder bodyText = new StringBuilder();
        for (int i = verseNumber; i >= 1; i--) {
            if (i == 1) {
                if (verseNumber > 1) {
                    bodyText.append("and a ");
                    bodyText.append(verses.get(i).text);
                } else {
                    bodyText.append("a ");
                    bodyText.append(verses.get(i).text);
                }
            } else {
                bodyText.append(verses.get(i).text);
                bodyText.append(", ");
            }
        }
        return mainText.formatted(verses.get(verseNumber).number, bodyText.toString());
    }

    String verses(int startVerse, int endVerse) {
        StringBuilder text = new StringBuilder();
        for (int i=startVerse; i <= endVerse; i++) {
            text.append(verse(i));
            if (i < endVerse) {
                text.append('\n');
            }
        }
        return text.toString();
    }

    String sing() {
        return verses(1, 12);
    }
}
