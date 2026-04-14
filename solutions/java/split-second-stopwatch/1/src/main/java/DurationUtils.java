import java.time.Duration;
import java.time.LocalTime;

public class DurationUtils {
    static String formatHms(Duration d) {
        long hours = d.toHours();
        long minutes = d.toMinutesPart();
        long seconds = d.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    static Duration parseHms(String timeString) {
        LocalTime time = LocalTime.parse(timeString); // expects HH:mm:ss
        return Duration.between(LocalTime.MIDNIGHT, time);
    }
}
