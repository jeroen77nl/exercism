import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

class Meetup {

    private final int monthOfYear;
    private final int year;

    Meetup(int monthOfYear, int year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
    }

    LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        return switch (schedule) {
            case TEENTH -> findNextDayOfWeek(dayOfWeek, 13);
            case FIRST -> findNextDayOfWeek(dayOfWeek, 1);
            case SECOND -> findNextDayOfWeek(dayOfWeek, 8);
            case THIRD -> findNextDayOfWeek(dayOfWeek, 15);
            case FOURTH -> findNextDayOfWeek(dayOfWeek, 22);
            case LAST -> findPrevDayOfWeek(dayOfWeek);
        };
    }

    private LocalDate findNextDayOfWeek(DayOfWeek dayOfWeek, int dayOfMonthStart) {
        return LocalDate.of(year, monthOfYear, dayOfMonthStart)
                .with(TemporalAdjusters.nextOrSame(dayOfWeek));
    }

    private LocalDate findPrevDayOfWeek(DayOfWeek dayOfWeek) {
        return LocalDate.of(year, monthOfYear, 1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .with(TemporalAdjusters.previousOrSame(dayOfWeek));
    }

}