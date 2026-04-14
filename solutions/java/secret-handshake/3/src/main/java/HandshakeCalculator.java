import java.util.List;

class HandshakeCalculator {

    private record SignalPair(Signal signal, int value) {
    }

    private static final List<SignalPair> SIGNAL_PAIRS = List.of(
            new SignalPair(Signal.WINK, 1),
            new SignalPair(Signal.DOUBLE_BLINK, 2),
            new SignalPair(Signal.CLOSE_YOUR_EYES, 4),
            new SignalPair(Signal.JUMP, 8)
    );

    private static final int REVERSE_MASK = 16;

    List<Signal> calculateHandshake(int number) {

        List<Signal> handshake = SIGNAL_PAIRS.stream()
                .filter(pair -> (number & pair.value()) != 0)
                .map(SignalPair::signal)
                .toList();

        return (number & REVERSE_MASK) != 0
                ? handshake.reversed()
                : handshake;
    }
}
