import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CustomSet<T> {

    private final List<T> set;

    CustomSet() {
        this.set = new ArrayList<>();
    }

    CustomSet(Collection<T> data) {
        this.set = new ArrayList<>();
        for (var item : data) {
            if (!set.contains(item)) {
                set.add(item);
            }
        }
    }

    boolean isEmpty() {
        return set.isEmpty();
    }

    boolean contains(T element) {
        return this.set.contains(element);
    }

    boolean isDisjoint(CustomSet<T> other) {
        for (var item : other.set) {
            if (this.set.contains(item)) {
                return false;
            }
        }
        return true;
    }

    boolean add(T element) {
        if (!set.contains(element)) {
            set.add(element);
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof CustomSet<?> customSet)) return false;
        return customSet.set.size() == this.set.size() && set.containsAll(customSet.set);
    }

    CustomSet<T> getIntersection(CustomSet<T> other) {
        CustomSet<T> result = new CustomSet<>();
        for (var item : set) {
            if (other.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    CustomSet<T> getUnion(CustomSet<T> other) {
        CustomSet<T> result = new CustomSet<>(this.set);
        for (var item : other.set) {
            result.add(item);
        }
        return result;
    }

CustomSet<T> getDifference(CustomSet<T> other) {
    CustomSet<T> result = new CustomSet<>();
    for (var item : set) {
        if (!other.contains(item)) {
            result.add(item);
        }
    }
    return result;
}

boolean isSubset(CustomSet<T> other) {
    for (var item : other.set) {
        if (!set.contains(item)) {
            return false;
        }
    }
    return true;
}
}
