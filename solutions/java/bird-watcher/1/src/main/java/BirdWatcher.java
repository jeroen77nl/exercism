import java.util.stream.*;

class BirdWatcher {
    private final int[] birdsPerDay;
    private final int[] lastWeek = new int[] { 0, 2, 5, 3, 7, 8, 4 };

    public BirdWatcher(int[] birdsPerDay) {
        this.birdsPerDay = birdsPerDay.clone();
    }

    public int[] getLastWeek() {
        return this.lastWeek;
    }

    public int getToday() {
        return birdsPerDay[birdsPerDay.length - 1];
    }

    public void setToday(int value) {
        birdsPerDay[birdsPerDay.length - 1] = value;
    }

    public void incrementTodaysCount() {
        setToday(getToday() + 1);
    }

    public boolean hasDayWithoutBirds() {
        return IntStream.of(birdsPerDay).anyMatch(e -> e == 0);
    }

    public int getCountForFirstDays(int numberOfDays) {
        return IntStream.of(birdsPerDay).limit(numberOfDays).sum();
    }

    public int getBusyDays() {
        return ((int)IntStream.of(birdsPerDay)
                    .filter(e -> e >= 5)
                    .count());
    }
}
