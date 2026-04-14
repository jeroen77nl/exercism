import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtils {

    static int quarter(LocalDateTime dateTime) {
        return switch (dateTime.getMonthValue()) {
            case 1, 2, 3 -> 1;
            case 4, 5, 6 -> 2;
            case 7, 8, 9 -> 3;
            default -> 4;
        };
    }

    static int lastMonthInQuarter(int quarter) {
        return switch (quarter) {
            case 1 -> 3;
            case 2 -> 6;
            case 3 -> 9;
            default -> 12;
        };
    }

    static LocalDate nextWorkingDay(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case SATURDAY -> date.plusDays(2);
            case SUNDAY -> date.plusDays(1);
            default -> date;
        };
    }

    static LocalDate previousWorkingDay(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case SATURDAY -> date.minusDays(1);
            case SUNDAY -> date.minusDays(2);
            default -> date;
        };
    }
}
