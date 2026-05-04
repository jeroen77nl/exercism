import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class BafflingBirthdays {
    private Random random = new Random();

    boolean sharedBirthday(List<LocalDate> birthdates) {
        return birthdates.stream()
                .map(bd -> LocalDate.of(1976, bd.getMonthValue(), bd.getDayOfMonth()))
                .collect(Collectors.toSet())
                .size()
                < birthdates.size();
    }

    List<LocalDate> randomBirthdates(int groupSize) {
        List<LocalDate> result = new ArrayList<>();
        for (int i = 1; i <=groupSize; i++ ) {
            result.add(randomBirthDate());
        }
        return result;
    }

    private LocalDate randomBirthDate() {
        int year = random.nextInt(1950, 2026);
        while (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            year = random.nextInt(1950, 2026);
        }

        int month = random.nextInt(1, 12);

        int day;
        switch (month) {
            case 1,3,5,7,8,10,12:
                day = random.nextInt(1, 31);
                break;
            case 2:
                day = random.nextInt(1,28);
                break;
            default:
                day = 30;
        }

        return LocalDate.of(year, month, day);
    }

    double estimatedProbabilityOfSharedBirthday(int groupSize) {
        final int DAYS_IN_YEAR = 365;
        if (groupSize <= 1) return 0.0;

        BigInteger vnr = factorial(DAYS_IN_YEAR)
                .divide(factorial(DAYS_IN_YEAR - groupSize));
        BigInteger vt = BigInteger.valueOf(DAYS_IN_YEAR).pow(groupSize);

        return (1 - vnr.doubleValue() / vt.doubleValue()) * 100;
    }

    public static BigInteger factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}