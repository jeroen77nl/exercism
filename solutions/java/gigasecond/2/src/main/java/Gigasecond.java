import java.time.LocalDate;
import java.time.LocalDateTime;

public class Gigasecond {

    private static final long GIGA_SECOND = 1_000_000_000L;
    private final LocalDateTime dateTime;

    public Gigasecond(LocalDate moment) {
        dateTime = moment.atStartOfDay();
    }

    public Gigasecond(LocalDateTime moment) {
        dateTime = moment;
    }

    public LocalDateTime getDateTime() {
        return dateTime.plusSeconds(GIGA_SECOND);
    }
}
