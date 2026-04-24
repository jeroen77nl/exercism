import java.util.ArrayList;
import java.util.List;

class Series {

    private final String text;

    Series(String text) {
        if (text.isEmpty()) {
            throw new IllegalArgumentException("series cannot be empty");
        }
        this.text = text;
    }

    List<String> slices(int num) {
        if (num > text.length()) {
            throw new IllegalArgumentException("slice length cannot be greater than series length");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("slice length cannot be negative or zero");
        }

        List<String> result = new ArrayList<>();

        for (int i = 0; i < text.length() - num + 1; i++) {
            result.add(text.substring(i, i + num));
        }

        return result;
    }
}
