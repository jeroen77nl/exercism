import java.util.ArrayList;
import java.util.List;

class FoodChain {

    private final String[] openingLines = {
            "It wriggled and jiggled and tickled inside her.\n",
            "How absurd to swallow a bird!\n",
            "Imagine that, to swallow a cat!\n",
            "What a hog, to swallow a dog!\n",
            "Just opened her throat and swallowed a goat!\n",
            "I don't know how she swallowed a cow!\n"
    };

    private final String[] animals = {
            "fly",
            "spider",
            "bird",
            "cat",
            "dog",
            "goat",
            "cow",
            "horse"
    };

    String verse(int verse) {
        if (verse < 1 || verse > 8) {
            throw new IllegalArgumentException("verse must be between 1 and 8");
        }

        StringBuilder result = new StringBuilder();

        String OLD_LADY_WHO_SWALLOWED = "I know an old lady who swallowed a %s.\n";
        result.append(OLD_LADY_WHO_SWALLOWED.formatted(animals[verse - 1]));

        if (verse == 8) {
            result.append("She's dead, of course!");
            return result.toString();
        }

        if (verse > 1) {
            result.append(openingLines[verse - 2]);
        }

        for (int i = verse - 1; i >= 1; i--) {
            result.append("She swallowed the %s to catch the %s."
                    .formatted(animals[i], animals[i - 1]));
            if (i == 2) {
                result.replace(result.length() - 1, result.length(), " ");
                result.append("that wriggled and jiggled and tickled inside her.");
            }
            result.append("\n");
        }
        result.append("I don't know why she swallowed the fly. Perhaps she'll die.");
        return result.toString();
    }

    String verses(int startVerse, int endVerse) {
        List<String> results = new ArrayList<>();

        for (int i = startVerse; i <= endVerse; i++) {
            results.add(verse(i));
        }

        return String.join("\n\n", results);
    }

}