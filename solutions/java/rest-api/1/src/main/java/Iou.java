import java.util.Comparator;

/**
 * POJO representing an IOU.
 * 
 * If this is in a user's "owed", then the user owes the person with {@link name} this {@link amount}.
 * If this is in a user's "owedBy", then {@link name} owes the user this {@link amount}.
 */
public class Iou implements Comparable<Iou> {
    public final String name;
    public final double amount;

    public Iou(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public int compareTo(Iou other) {
        return this.name.compareTo(other.name);
    }
}
