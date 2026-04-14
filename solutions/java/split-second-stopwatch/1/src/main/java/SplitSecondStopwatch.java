import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SplitSecondStopwatch {

    enum StopwatchState {
        READY, RUNNING, STOPPED
    }

    private StopwatchState state = StopwatchState.READY;
    private final List<Duration> previousLaps = new ArrayList<>();
    private Duration currentLap = Duration.ZERO;

    public void start() {
        if (state == StopwatchState.RUNNING) {
            throw new IllegalStateException(
                    "cannot start an already running stopwatch");
        }
        this.state = StopwatchState.RUNNING;
    }

    public void stop() {
        if (state != StopwatchState.RUNNING) {
            throw new IllegalStateException("cannot stop a stopwatch that is not running");
        }
        this.state = StopwatchState.STOPPED;
    }

    public void reset() {
        if (state != StopwatchState.STOPPED) {
            throw new IllegalStateException("cannot reset a stopwatch that is not stopped");
        }
        this.state = StopwatchState.READY;
        this.currentLap = Duration.ZERO;
        this.previousLaps.clear();
    }

    public void lap() {
        if (state != StopwatchState.RUNNING) {
            throw new IllegalStateException("cannot lap a stopwatch that is not running");
        }
        previousLaps.add(currentLap);
        currentLap = Duration.ZERO;
    }

    public String state() {
        return this.state.toString().toLowerCase();
    }

    public String currentLap() {
        return DurationUtils.formatHms(this.currentLap);
    }

    public String total() {
        Duration total = currentLap;
        for (Duration duration : previousLaps) {
            total = total.plus(duration);
        }
        return DurationUtils.formatHms(total);
    }

    public java.util.List<String> previousLaps() {
        return this.previousLaps.stream()
                .map(DurationUtils::formatHms)
                .toList();
    }

    public void advanceTime(String timeString) {
        if (state == StopwatchState.RUNNING) {
            Duration advanceTime = DurationUtils.parseHms(timeString);
            this.currentLap = currentLap.plus(advanceTime);
        }
    }

}