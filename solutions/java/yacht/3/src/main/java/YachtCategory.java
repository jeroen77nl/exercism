public enum YachtCategory {

    YACHT,
    ONES,
    TWOS,
    THREES,
    FOURS,
    FIVES,
    SIXES,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    LITTLE_STRAIGHT,
    BIG_STRAIGHT,
    CHOICE;

    public int getFixedValue() {
        return switch(this) {
            case ONES -> 1;
            case TWOS -> 2;
            case THREES -> 3;
            case FOURS -> 4;
            case FIVES -> 5;
            case SIXES -> 6;
            default -> 0;
        };
    }

}
