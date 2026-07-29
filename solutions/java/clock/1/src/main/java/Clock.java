class Clock {

    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;
    private static final int MINUTES_IN_DAY = MINUTES_IN_HOUR * HOURS_IN_DAY;

    private int minutes;

    Clock(int hours, int minutes) {
        this.minutes = normalize(hours * MINUTES_IN_HOUR + minutes);
    }

    void add(int minutes) {
        this.minutes += minutes;
        this.minutes = normalize(this.minutes);
    }

    private static int normalize(int minutes) {
        int normalizedMinutes = minutes % MINUTES_IN_DAY;
        if (normalizedMinutes < 0) {
            normalizedMinutes += MINUTES_IN_DAY;
        }
        return normalizedMinutes;
    }

    @Override
    public String toString() {
        return String.format(
                "%02d:%02d",
                this.minutes / MINUTES_IN_HOUR,
                this.minutes % MINUTES_IN_HOUR);
    }

    @Override
    public int hashCode() {
        return minutes;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Clock clock)) return false;

        return clock.minutes == this.minutes;
    }
}