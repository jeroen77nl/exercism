class Leap {

    boolean isLeapYear(int year) {
        return deelbaar(year, 4) &&
                (!deelbaar(year, 100) || deelbaar(year, 400));
    }

    boolean deelbaar(int teller, int noemer) {
        return teller % noemer == 0;
    }
}
