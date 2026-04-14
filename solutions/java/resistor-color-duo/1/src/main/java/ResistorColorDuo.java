import java.util.List;

class ResistorColorDuo {

    int value(String[] colors) {
        String duoCode = "" + ResistorColor.colorCode(colors[0]) + ResistorColor.colorCode(colors[1]);
        return Integer.parseInt(duoCode);
    }
}
