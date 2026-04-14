import java.util.List;

class ResistorColor {
    private static final List<String> COLORS = List.of(
            "black", "brown", "red", "orange", "yellow",
            "green", "blue", "violet", "grey", "white"
    );

    static int colorCode(String color) {
        return COLORS.indexOf(color);
    }

    static String[] colors() {
        return COLORS.toArray(String[]::new);
    }
}