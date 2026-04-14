class ResistorColorDuo {

    static int value(String[] colors) {
        return ResistorColor.colorCode(colors[0]) * 10
                + ResistorColor.colorCode(colors[1]);
    }
}
