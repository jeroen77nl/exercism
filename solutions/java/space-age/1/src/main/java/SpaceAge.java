class SpaceAge {

    private static final int SECONDS_IN_DAY = 24 * 60 * 60;
    private static final double DAYS_IN_YEAR = 365.25;
    private static final double SECONDS_IN_YEAR = SECONDS_IN_DAY * DAYS_IN_YEAR;

    private final double seconds;

    SpaceAge(double seconds) {
        this.seconds = seconds;
    }

    double onEarth() {
        return seconds / (Planet.EARTH.getTimeFactor() * SECONDS_IN_YEAR);
    }

    double onMercury() {
        return seconds / (Planet.MERCURY.getTimeFactor() * SECONDS_IN_YEAR);
    }

    double onVenus() {
    return seconds / (Planet.VENUS.getTimeFactor() * SECONDS_IN_YEAR);
    }

    double onMars() {
        return seconds / (Planet.MARS.getTimeFactor() * SECONDS_IN_YEAR);
    }

    double onJupiter() {
        return seconds / (Planet.JUPITER.getTimeFactor() * SECONDS_IN_YEAR);
    }

    double onSaturn() {
        return seconds / (Planet.SATURN.getTimeFactor() * SECONDS_IN_YEAR);
    }

    double onUranus() {
        return seconds / (Planet.URANUS.getTimeFactor() * SECONDS_IN_YEAR);
    }

    double onNeptune() {
        return seconds / (Planet.NEPTUNE.getTimeFactor() * SECONDS_IN_YEAR);
    }

}
