class Leap {

    boolean isLeapYear(int year) {
        return deelbaar(year, 400) ||
                (deelbaar(year, 4) && !deelbaar(year, 100));
    }

    boolean deelbaar(int teller, int noemer) {
        return teller % noemer == 0;
    }
}
