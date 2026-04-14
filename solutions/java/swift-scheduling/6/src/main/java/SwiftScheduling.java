import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SUNDAY;

public class SwiftScheduling {

    private static final LocalTime T_0800 = LocalTime.of(8, 0);
    private static final LocalTime T_1300 = LocalTime.of(13, 0);
    private static final LocalTime T_1700 = LocalTime.of(17, 0);
    private static final LocalTime T_2000 = LocalTime.of(20, 0);

    private static final List<Rule> RULES = List.of(
            new Rule("NOW"::equals, (_, t) -> SwiftScheduling.handleNOW(t)),
            new Rule("ASAP"::equals, (_, t) -> SwiftScheduling.handleASAP(t)),
            new Rule("EOW"::equals, (_, t) -> SwiftScheduling.handleEOW(t)),
            new Rule(d -> d.endsWith("M"), SwiftScheduling::handleM),
            new Rule(d -> d.startsWith("Q"), SwiftScheduling::handleQ)
    );

    public static LocalDateTime convertToDeliveryDate(
            LocalDateTime meetingStart, String description) {

        return RULES.stream()
                .filter(rule -> rule.matches(description))
                .findFirst()
                .map(rule -> rule.apply(description, meetingStart))
                .orElseThrow(() -> new IllegalArgumentException("unknown description " + description));
    }

    private static LocalDateTime handleNOW(LocalDateTime meetingStart) {
        return meetingStart.plusHours(2);
    }

    private static LocalDateTime handleASAP(LocalDateTime meetingStart) {
        if (meetingStart.getHour() < 13) {
            return LocalDateTime.of(
                    meetingStart.toLocalDate(),
                    T_1700);
        } else {
            return LocalDateTime.of(
                    meetingStart.toLocalDate().plusDays(1),
                    T_1300);
        }
    }

    private static LocalDateTime handleEOW(LocalDateTime meetingStart) {
        return switch (meetingStart.getDayOfWeek()) {
            case MONDAY, TUESDAY, WEDNESDAY -> meetingStart
                    .with(TemporalAdjusters.nextOrSame(FRIDAY))
                    .with(T_1700);
            case THURSDAY, FRIDAY -> meetingStart
                    .with(TemporalAdjusters.nextOrSame(SUNDAY))
                    .with(T_2000);
            default -> throw new IllegalArgumentException("meetingStart is not on a working day");
        };
    }

    private static LocalDateTime handleM(String description, LocalDateTime meetingStart) {
        int targetMonth = parseMonth(description);
        int year = meetingStart.getMonthValue() < targetMonth
                ? meetingStart.getYear()
                : meetingStart.getYear() + 1;

        LocalDate firstOfTargetMonth = LocalDate.of(year, targetMonth, 1);

        return DateUtils.nextWorkingDay(firstOfTargetMonth)
                .atTime(T_0800);
    }

    private static LocalDateTime handleQ(String description, LocalDateTime meetingStart) {
        int targetQuarter = parseQuarter(description);
        int year = DateUtils.quarter(meetingStart) <= targetQuarter
                ? meetingStart.getYear()
                : meetingStart.getYear() + 1;

        LocalDate lastDayOfQuarter = LocalDate.of(year, DateUtils.lastMonthInQuarter(targetQuarter), 1)
                .plusMonths(1)
                .minusDays(1);

        return DateUtils.previousWorkingDay(lastDayOfQuarter)
                .atTime(T_0800);
    }

    private static int parseMonth(String description) {
        int m = Integer.parseInt(description.substring(0, description.length() - 1));
        if (m < 1 || m > 12) {
            throw new IllegalArgumentException("Invalid month: " + description);
        }
        return m;
    }

    private static int parseQuarter(String description) {
        int q = Character.getNumericValue(description.charAt(1));
        if (q < 1 || q > 4) {
            throw new IllegalArgumentException("Invalid quarter: " + description);
        }
        return q;
    }
}
