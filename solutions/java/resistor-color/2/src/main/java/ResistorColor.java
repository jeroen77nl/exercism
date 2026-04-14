import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ResistorColor {
    private static final List<String> COLORS = List.of(
            "black", "brown", "red", "orange", "yellow",
            "green", "blue", "violet", "grey", "white"
    );

    int colorCode(String color) {
        return COLORS.indexOf(color);
    }

    String[] colors() {
        return COLORS.toArray(String[]::new);
    }
}