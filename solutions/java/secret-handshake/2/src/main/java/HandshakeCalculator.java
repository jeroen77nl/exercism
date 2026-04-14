import java.util.ArrayList;
import java.util.List;

class HandshakeCalculator {

    private record SignalPair(Signal signal, int value) {}

    private static final List<SignalPair> SIGNAL_PAIRS = List.of(
            new SignalPair(Signal.WINK, 1),
            new SignalPair(Signal.DOUBLE_BLINK, 2),
            new SignalPair(Signal.CLOSE_YOUR_EYES, 4),
            new SignalPair(Signal.JUMP, 8)
    );

    List<Signal> calculateHandshake(int number) {
        List<Signal> result = new ArrayList<>();

        for (var signalPair : SIGNAL_PAIRS) {
            if ((number & signalPair.value()) != 0)
                result.add(signalPair.signal());
        }

        if ((number & 16) != 0) result = result.reversed();

        return result;
    }
}
