import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

class Meetup {

    private final int monthOfYear;
    private final int year;

    Meetup(int monthOfYear, int year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
    }

    LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        return switch(schedule) {
            case TEENTH -> teenth(dayOfWeek);
            case FIRST -> first(dayOfWeek);
            case SECOND -> second(dayOfWeek);
            case THIRD -> third(dayOfWeek);
            case FOURTH -> fourth(dayOfWeek);
            case LAST -> last(dayOfWeek);
            default -> throw new RuntimeException("Not yet implemented schedule " + schedule);
        };
    }

    private LocalDate first(DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.of(year, monthOfYear, 1);
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.plusDays(1);
        }
        return date;
    }

    private LocalDate second(DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.of(year, monthOfYear, 8);
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.plusDays(1);
        }
        return date;
    }

    private LocalDate third(DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.of(year, monthOfYear, 15);
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.plusDays(1);
        }
        return date;
    }

    private LocalDate fourth(DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.of(year, monthOfYear, 22);
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.plusDays(1);
        }
        return date;
    }

    private LocalDate last(DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.of(year, monthOfYear, 1)
                .plusMonths(1)
                .minusDays(1);
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.minusDays(1);
        }
        return date;
    }

    private LocalDate teenth(DayOfWeek dayOfWeek) {
        LocalDate teenthDay = LocalDate.of(year, monthOfYear, 13);
        while (teenthDay.getDayOfWeek() != dayOfWeek) {
            teenthDay = teenthDay.plusDays(1);
        }
        return teenthDay;
    }

}