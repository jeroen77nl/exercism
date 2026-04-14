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

    private final Map<String, ClientRate> clientRates = new HashMap<>();

    public RateLimiter(int limit, Duration windowSize, TimeSource timeSource) {
        this.limit = limit;
        this.timeSource = timeSource;
        this.windowSize = windowSize;
    }

    public boolean allow(K clientId) {
        Instant now = timeSource.now();

        ClientRate clientRate = clientRates.get(clientId.toString());
        if (clientRate == null) {
            clientRates.put(clientId.toString(), new ClientRate(now, 1));
            return true;
        }

        Instant windowEnd = clientRate.windowStart().plus(windowSize);
        if (!now.isBefore(windowEnd)) {
            clientRates.put(clientId.toString(), new ClientRate(now, 1));
            return true;
        }

        if (clientRate.requests() < limit) {
            clientRates.put(clientId.toString(), new ClientRate(now, clientRate.requests() + 1));
            return true;
        }
        return false;
    }
}
