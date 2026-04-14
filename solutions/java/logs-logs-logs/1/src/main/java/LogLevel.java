public enum LogLevel {
    TRACE("TRC", 1), 
    DEBUG("DBG", 2), 
    INFO("INF", 4), 
    WARNING("WRN", 5), 
    ERROR("ERR", 6), 
    FATAL("FTL", 42), 
    UNKNOWN("UNKNOWN", 0);

    private final String description;
    private final int level;

    LogLevel(String description, int level) {
        this.description = description;
        this.level = level;
    }

    public String getDescription() {
        return this.description;
    }

    public int getLevel() {
        return this.level;
    }
}
