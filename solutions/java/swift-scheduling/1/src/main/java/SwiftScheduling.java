import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class SwiftScheduling {
    public static LocalDateTime convertToDeliveryDate(
            LocalDateTime meetingStart, String description) {
        if ("NOW".equals(description)) {
            return handleNOW(meetingStart);
        } else if ("ASAP".equals(description)) {
            return handleASAP(meetingStart);
        } else if ("EOW".equals(description)) {
            return handleEOW(meetingStart);
        } else if (description.endsWith("M")) {
            return handleM(description, meetingStart);
        } else if (description.startsWith("Q")) {
            return handleQ(description, meetingStart);
        } else {
            return null;
        }
    }

    private static LocalDateTime handleNOW(LocalDateTime meetingStart) {
        return meetingStart.plusHours(2);
    }

    private static LocalDateTime handleASAP(LocalDateTime meetingStart) {
        if (meetingStart.getHour() < 13) {
            return LocalDateTime.of(
                    meetingStart.toLocalDate(),
                    LocalTime.of(17, 0, 0));
        } else {
            return LocalDateTime.of(
                    meetingStart.toLocalDate().plusDays(1),
                    LocalTime.of(13, 0, 0));
        }
    }

    private static LocalDateTime handleEOW(LocalDateTime meetingStart) {
        LocalDateTime result = meetingStart;
        if (List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY)
                .contains(meetingStart.getDayOfWeek())) {
            while (result.getDayOfWeek() != DayOfWeek.FRIDAY) {
                result = result.plusDays(1);
            }
            return LocalDateTime.of(
                    result.toLocalDate(),
                    LocalTime.of(17, 0, 0));
        } else if (List.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
                .contains(meetingStart.getDayOfWeek())) {
            while (result.getDayOfWeek() != DayOfWeek.SUNDAY) {
                result = result.plusDays(1);
            }
            return LocalDateTime.of(
                    result.toLocalDate(),
                    LocalTime.of(20, 0, 0));
        } else {
            throw new IllegalArgumentException("meetingStart is not on a working day");
        }
    }

    private static LocalDateTime handleM(String description, LocalDateTime meetingStart) {
        String month = description.split("M")[0];
        int nThMonth = Integer.parseInt(month);
        LocalDate date;
        if (meetingStart.getMonthValue() < nThMonth) {
            date = LocalDate.of(meetingStart.getYear(), nThMonth, 1);
        } else {
            date = LocalDate.of(meetingStart.getYear() + 1, nThMonth, 1);
        }
        while (List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(date.getDayOfWeek())) {
            date = date.plusDays(1);
        }
        LocalTime time = LocalTime.of(8, 0, 0);
        return LocalDateTime.of(date, time);
    }

    private static LocalDateTime handleQ(String description, LocalDateTime meetingStart) {
        int nThQuarter = description.charAt(1) - '0';
        LocalDate date;
        if (quarter(meetingStart) <= nThQuarter) {
            date = LocalDate.of(meetingStart.getYear(), lastMonthInQuarter(nThQuarter), 1);
        } else {
            date = LocalDate.of(meetingStart.getYear() + 1, lastMonthInQuarter(nThQuarter), 1);
        }
        date = date.plusMonths(1).minusDays(1);
        while (List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(date.getDayOfWeek())) {
            date = date.minusDays(1);
        }
        LocalTime time = LocalTime.of(8, 0, 0);
        return LocalDateTime.of(date, time);
    }

    private static int quarter(LocalDateTime dateTime) {
        return switch (dateTime.getMonthValue()) {
            case 1, 2, 3 -> 1;
            case 4, 5, 6 -> 2;
            case 7, 8, 9 -> 3;
            default -> 4;
        };
    }

    private static int lastMonthInQuarter(int quarter) {
        return switch (quarter) {
            case 1 -> 3;
            case 2 -> 6;
            case 3 -> 9;
            default -> 12;
        };
    }
}
