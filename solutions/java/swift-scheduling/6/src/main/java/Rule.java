import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Predicate;

record Rule(
        Predicate<String> predicate,
        BiFunction<String, LocalDateTime, LocalDateTime> handler) {

    boolean matches(String description) {
        return predicate.test(description);
    }

    LocalDateTime apply(String description, LocalDateTime meetingStart) {
        return handler.apply(description, meetingStart);
    }
}