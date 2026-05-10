import java.util.ArrayList;
import java.util.List;

class House {

    private final String[] texts = {
            "the malt that lay in",
            "the rat that ate",
            "the cat that killed",
            "the dog that worried",
            "the cow with the crumpled horn that tossed",
            "the maiden all forlorn that milked",
            "the man all tattered and torn that kissed",
            "the priest all shaven and shorn that married",
            "the rooster that crowed in the morn that woke",
            "the farmer sowing his corn that kept",
            "the horse and the hound and the horn that belonged to"
    };

    String verse(int verse) {
        return "This is"
                + buildVerse(verse - 2)
                + " the house that Jack built.";
    }

    private String buildVerse(int index) {
        if (index < 0) {
            return "";
        }

        return " "
                + texts[index]
                + buildVerse(index - 1);
    }

    String verses(int startVerse, int endVerse) {
        List<String> verses = new ArrayList<>();

        for (int i = startVerse; i <= endVerse; i++) {
            verses.add(verse(i));
        }

        return String.join("\n", verses);
    }

    String sing() {
        return verses(1, 12);
    }

}