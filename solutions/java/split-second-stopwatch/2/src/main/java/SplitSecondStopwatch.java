import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SplitSecondStopwatch {

    private StopwatchState state = StopwatchState.READY;

    private final List<Duration> previousLaps = new ArrayList<>();
    private Duration currentLap = Duration.ZERO;

    // --- public API ---

    public void start() {
        state.start(this);
    }

    public void stop() {
        state.stop(this);
    }

    public void reset() {
        state.reset(this);
    }

    public void lap() {
        state.lap(this);
    }

    public void advanceTime(String timeString) {
        state.advance(this, DurationUtils.parseHms(timeString));
    }

    // --- queries ---

    public String state() {
        return state.name().toLowerCase();
    }

    public String currentLap() {
        return DurationUtils.formatHms(currentLap);
    }

    public String total() {
        Duration total = currentLap;
        for (Duration d : previousLaps) {
            total = total.plus(d);
        }
        return DurationUtils.formatHms(total);
    }

    public List<String> previousLaps() {
        return previousLaps.stream()
                .map(DurationUtils::formatHms)
                .toList();
    }

    // --- state machine ---

    private enum StopwatchState {

        READY {
            @Override
            void start(SplitSecondStopwatch s) {
                s.state = RUNNING;
            }

            @Override
            void stop(SplitSecondStopwatch s) {
                illegal("cannot stop a stopwatch that is not running");
            }

            @Override
            void reset(SplitSecondStopwatch s) {
                illegal("cannot reset a stopwatch that is not stopped");
            }

            @Override
            void lap(SplitSecondStopwatch s) {
                illegal("cannot lap a stopwatch that is not running");
            }

            @Override
            void advance(SplitSecondStopwatch s, Duration d) {
                // ignore
            }
        },

        RUNNING {
            @Override
            void start(SplitSecondStopwatch s) {
                illegal("cannot start an already running stopwatch");
            }

            @Override
            void stop(SplitSecondStopwatch s) {
                s.state = STOPPED;
            }

            @Override
            void reset(SplitSecondStopwatch s) {
                illegal("cannot reset a stopwatch that is not stopped");
            }

            @Override
            void lap(SplitSecondStopwatch s) {
                s.previousLaps.add(s.currentLap);
                s.currentLap = Duration.ZERO;
            }

            @Override
            void advance(SplitSecondStopwatch s, Duration d) {
                s.currentLap = s.currentLap.plus(d);
            }
        },

        STOPPED {
            @Override
            void start(SplitSecondStopwatch s) {
                s.state = RUNNING;
            }

            @Override
            void stop(SplitSecondStopwatch s) {
                illegal("cannot stop a stopwatch that is not running");
            }

            @Override
            void reset(SplitSecondStopwatch s) {
                s.state = READY;
                s.currentLap = Duration.ZERO;
                s.previousLaps.clear();
            }

            @Override
            void lap(SplitSecondStopwatch s) {
                illegal("cannot lap a stopwatch that is not running");
            }

            @Override
            void advance(SplitSecondStopwatch s, Duration d) {
                // ignore
            }
        };

        abstract void start(SplitSecondStopwatch s);
        abstract void stop(SplitSecondStopwatch s);
        abstract void reset(SplitSecondStopwatch s);
        abstract void lap(SplitSecondStopwatch s);
        abstract void advance(SplitSecondStopwatch s, Duration d);

        protected static void illegal(String message) {
            throw new IllegalStateException(message);
        }
    }
}