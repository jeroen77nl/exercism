import java.util.List;
import java.util.stream.Stream;

public class LogLine {

    private String logLine;

    public LogLine(String logLine) {
        this.logLine = logLine;
    }

    public LogLevel getLogLevel() {
        LogLevel result = Stream.of(LogLevel.values())
            .filter(level -> !level.equals(LogLevel.UNKNOWN))
            .filter(level -> this.logLine.contains("[" + level.getDescription() + "]"))
            .findFirst()
            .orElse(LogLevel.UNKNOWN);  
        return result;
    }

    public String getOutputForShortLog() {
        String message = this.logLine.split(":")[1].trim();
        return String.format("%d:%s", getLogLevel().getLevel(), message);
    }
}
