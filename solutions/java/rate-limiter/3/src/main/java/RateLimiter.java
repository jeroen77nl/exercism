import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter<K> {

    private final int limit;
    private final TimeSource timeSource;
    private final Duration windowSize;

    record ClientRate(Instant windowStart, int requests) {
    }

    private final Map<K, ClientRate> clientRates = new HashMap<>();

    public RateLimiter(int limit, Duration windowSize, TimeSource timeSource) {
        this.limit = limit;
        this.timeSource = timeSource;
        this.windowSize = windowSize;
    }

    public boolean allow(K clientId) {
        Instant now = timeSource.now();

        ClientRate clientRate = clientRates.get(clientId);
        if (clientRate == null) {
            return saveAllowedRate(clientId, new ClientRate(now, 1));
        }

        Instant windowEnd = clientRate.windowStart().plus(windowSize);
        if (!now.isBefore(windowEnd)) {
            return saveAllowedRate(clientId, new ClientRate(now, 1));
        }

        if (clientRate.requests() < limit) {
            return saveAllowedRate(clientId, new ClientRate(clientRate.windowStart(), clientRate.requests() + 1));
        }
        return false;
    }

    private boolean saveAllowedRate(K clientId, ClientRate newRate) {
        clientRates.putIfAbsent(clientId, newRate);
        return true;
    }
}
