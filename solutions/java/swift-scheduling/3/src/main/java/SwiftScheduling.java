import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SUNDAY;

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
            throw new IllegalArgumentException("unknown description " + description);
        }
    }

    private static LocalDateTime handleNOW(LocalDateTime meetingStart) {
        return meetingStart.plusHours(2);
    }

    private static LocalDateTime handleASAP(LocalDateTime meetingStart) {
        if (meetingStart.getHour() < 13) {
            return LocalDateTime.of(
                    meetingStart.toLocalDate(),
                    LocalTime.of(17, 0));
        } else {
            return LocalDateTime.of(
                    meetingStart.toLocalDate().plusDays(1),
                    LocalTime.of(13, 0));
        }
    }

    private static LocalDateTime handleEOW(LocalDateTime meetingStart) {
        return switch (meetingStart.getDayOfWeek()) {
            case MONDAY, TUESDAY, WEDNESDAY -> meetingStart
                    .with(TemporalAdjusters.nextOrSame(FRIDAY))
                    .with(LocalTime.of(17, 0));
            case THURSDAY, FRIDAY -> meetingStart
                    .with(TemporalAdjusters.nextOrSame(SUNDAY))
                    .with(LocalTime.of(20, 0));
            default -> throw new IllegalArgumentException("meetingStart is not on a working day");
        };
    }

    private static LocalDateTime handleM(String description, LocalDateTime meetingStart) {
        int targetMonth = Integer.parseInt(description.replace("M", ""));

        int year = meetingStart.getMonthValue() < targetMonth
                ? meetingStart.getYear()
                : meetingStart.getYear() + 1;

        LocalDate firstOfTargetMonth = LocalDate.of(year, targetMonth, 1);

        return DateUtils.nextWorkingDay(firstOfTargetMonth)
                .atTime(8, 0);    }

    private static LocalDateTime handleQ(String description, LocalDateTime meetingStart) {
        int targetQuarter = description.charAt(1) - '0';

        int year = DateUtils.quarter(meetingStart) <= targetQuarter
                ? meetingStart.getYear()
                : meetingStart.getYear() + 1;

        LocalDate lastDayOfQuarter = LocalDate.of(year, DateUtils.lastMonthInQuarter(targetQuarter), 1)
                .plusMonths(1)
                .minusDays(1);

        return DateUtils.previousWorkingDay(lastDayOfQuarter)
                .atTime(8, 0);
    }

}
