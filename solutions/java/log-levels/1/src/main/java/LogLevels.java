public class LogLevels {
    
    public static String message(String logLine) {
        logLine = logLine.replaceFirst("\\[\\w+]:", "");
        logLine = logLine.trim();
        return logLine;
    }

    public static String logLevel(String logLine) {
        String[] tokens = logLine.split(":");
        return tokens[0].substring(1, tokens[0].length() - 1).toLowerCase();
    }

    public static String reformat(String logLine) {
        return String.format("%s (%s)", message(logLine), logLevel(logLine));
    }
}
