import java.util.HashMap;
import java.util.Map;

class ResistorColor {
    private static final String[] COLORS = {
            "black", "brown", "red", "orange", "yellow",
            "green", "blue", "violet", "grey", "white"
    };

    private static final Map<String, Integer> COLOR_CODES = new HashMap<>();

    static {
        for (int i = 0; i < COLORS.length; i++) {
            COLOR_CODES.put(COLORS[i], i);
        }
    }

    int colorCode(String color) {
        Integer code = COLOR_CODES.get(color);
        if (code == null) {
            throw new IllegalArgumentException("Unknown color: " + color);
        }
        return code;
    }

    String[] colors() {
        return COLORS.clone();
    }
}
