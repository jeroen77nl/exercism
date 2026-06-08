import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;

enum Field {

    PIECES(
            d -> d.pieces,
            (d, v) -> d.pieces = (Integer) v,
            Objects::equals),
    BORDER(
            d -> d.border,
            (d, v) -> d.border = (Integer) v,
            Objects::equals),
    INSIDE(
            d -> d.inside,
            (d, v) -> d.inside = (Integer) v,
            Objects::equals),
    ROWS(
            d -> d.rows,
            (d, v) -> d.rows = (Integer) v,
            Objects::equals),
    COLUMNS(
            d -> d.columns,
            (d, v) -> d.columns = (Integer) v,
            Objects::equals),
    RATIO(
            d -> d.aspectRatio,
            (d, v) -> d.aspectRatio = (Double) v,
            (a, b) -> Math.abs((Double) a - (Double) b) < 0.0001),
    FORMAT(
            d -> d.format,
            (d, v) -> d.format = (String) v,
            Objects::equals);

    private final Function<RectangleData, Object> getter;
    private final BiConsumer<RectangleData, Object> setter;
    private final BiPredicate<Object, Object> comparator;

    Field(
            Function<RectangleData, Object> getter,
            BiConsumer<RectangleData, Object> setter,
            BiPredicate<Object, Object> comparator) {

        this.getter = getter;
        this.setter = setter;
        this.comparator = comparator;
    }

    boolean isPresent(RectangleData data) {
        return getter.apply(data) != null;
    }

    Object get(RectangleData data) {
        return getter.apply(data);
    }

    void set(RectangleData data, Object value) {
        setter.accept(data, value);
    }

    boolean matches(Object o1, Object o2) {
        return comparator.test(o1, o2);
    }
}

class RectangleData {
    Integer pieces;
    Integer border;
    Integer inside;
    Integer rows;
    Integer columns;
    Double aspectRatio;
    String format;
}

record Formula<T>(
        Set<Field> required,
        Field target,
        Function<RectangleData, T> calculation) {
}

public class PiecingItTogether {

    private static final List<Formula<?>> formulas = List.of(

            new Formula<>(
                    Set.of(Field.ROWS, Field.COLUMNS),
                    Field.RATIO,
                    d -> (double) d.columns / d.rows
            ),

            new Formula<>(
                    Set.of(Field.ROWS, Field.COLUMNS),
                    Field.PIECES,
                    d -> d.rows * d.columns
            ),

            new Formula<>(
                    Set.of(Field.ROWS, Field.COLUMNS),
                    Field.BORDER,
                    d -> 2 * d.rows + 2 * d.columns - 4
            ),

            new Formula<>(
                    Set.of(Field.PIECES, Field.ROWS),
                    Field.COLUMNS,
                    d -> d.pieces / d.rows
            ),

            new Formula<>(
                    Set.of(Field.PIECES, Field.RATIO),
                    Field.ROWS,
                    d -> (int) Math.round(Math.sqrt(d.pieces / d.aspectRatio))
            ),

            new Formula<>(
                    Set.of(Field.ROWS, Field.RATIO),
                    Field.COLUMNS,
                    d -> (int) Math.round(d.rows * d.aspectRatio)
            ),

            new Formula<>(
                    Set.of(Field.COLUMNS, Field.RATIO),
                    Field.ROWS,
                    d -> (int) Math.round(d.columns / d.aspectRatio)
            ),

            new Formula<>(
                    Set.of(Field.PIECES, Field.INSIDE),
                    Field.BORDER,
                    d -> d.pieces - d.inside
            ),

            new Formula<>(
                    Set.of(Field.PIECES, Field.BORDER),
                    Field.INSIDE,
                    d -> d.pieces - d.border
            ),

            new Formula<>(
                    Set.of(Field.PIECES, Field.BORDER, Field.FORMAT),
                    Field.ROWS,
                    d -> {
                        double s = (d.border + 4) / 2.0;
                        double t = Math.sqrt(s * s - 4 * d.pieces);

                        int x1 = (int) ((s + t) / 2);
                        int x2 = (int) ((s - t) / 2);

                        return switch (d.format) {
                            case "square" -> x1;
                            case "portrait" -> Math.max(x1, x2);
                            default -> Math.min(x1, x2);
                        };
                    }
            ),

            new Formula<>(
                    Set.of(Field.PIECES, Field.RATIO),
                    Field.COLUMNS,
                    d -> (int) Math.round(Math.sqrt(d.pieces * d.aspectRatio))
            ),

            new Formula<>(
                    Set.of(Field.INSIDE, Field.RATIO),
                    Field.COLUMNS,
                    d -> d.aspectRatio == 1.0
                            ? (int) Math.round(Math.sqrt(d.inside)) + 2
                            : null
            ),

            new Formula<>(
                    Set.of(Field.RATIO),
                    Field.FORMAT,
                    d ->
                            d.aspectRatio > 1
                                    ? "landscape"
                                    : (d.aspectRatio == 1
                                       ? "square"
                                       : "portrait")
            ),

            new Formula<>(
                    Set.of(Field.FORMAT),
                    Field.RATIO,
                    d ->
                            d.format.equals("square") ? 1.0 : null
            )
    );

    public static JigsawInfo getCompleteInformation(JigsawInfo input) {
        RectangleData rectangleData = fromInput(input);

        solve(rectangleData);

        if (rectangleDataIncomplete(rectangleData)) {
            throw new IllegalArgumentException("Insufficient data");
        }

        return fillOutput(rectangleData);
    }

    private static void solve(RectangleData rectangleData) {
        boolean repeat;
        do {
            repeat = false;

            for (Formula<?> f : formulas) {
                if (!allRequiredFieldsPresent(f, rectangleData))
                    continue;

                Object result = f.calculation().apply(rectangleData);
                if (result == null)
                    continue;

                Object existing = f.target().get(rectangleData);
                if (existing == null) {
                    f.target().set(rectangleData, result);
                    repeat = true;
                } else {
                    if (!f.target().matches(existing, result))
                        throw new IllegalArgumentException("Contradictory data");
                }
            }

        } while (repeat);
    }

    private static RectangleData fromInput(JigsawInfo input) {
        RectangleData rectangleData = new RectangleData();
        if (input.getColumns().isPresent())
            rectangleData.columns = input.getColumns().getAsInt();
        if (input.getRows().isPresent())
            rectangleData.rows = input.getRows().getAsInt();
        if (input.getPieces().isPresent())
            rectangleData.pieces = input.getPieces().getAsInt();
        if (input.getBorder().isPresent())
            rectangleData.border = input.getBorder().getAsInt();
        if (input.getInside().isPresent())
            rectangleData.inside = input.getInside().getAsInt();
        if (input.getAspectRatio().isPresent())
            rectangleData.aspectRatio = input.getAspectRatio().getAsDouble();
        if (input.getFormat().isPresent())
            rectangleData.format = input.getFormat().get();
        return rectangleData;
    }

    private static boolean allRequiredFieldsPresent(Formula<?> formula, RectangleData rectangleData) {
        return formula.required()
                .stream()
                .allMatch(field -> field.isPresent(rectangleData));
    }

    private static boolean rectangleDataIncomplete(RectangleData rectangleData) {
        return Arrays.stream(Field.values())
                .anyMatch(field -> !field.isPresent(rectangleData));
    }

    private static JigsawInfo fillOutput(RectangleData rectangleData) {
        JigsawInfo.Builder builder = new JigsawInfo.Builder();
        builder.pieces(rectangleData.pieces);
        builder.border(rectangleData.border);
        builder.inside(rectangleData.inside);
        builder.rows(rectangleData.rows);
        builder.columns(rectangleData.columns);
        builder.aspectRatio(rectangleData.aspectRatio);
        builder.format(rectangleData.format);
        return builder.build();
    }
}
