import java.util.List;

class BinarySearch {
    private final List<Integer> items;

    BinarySearch(List<Integer> items) {
        this.items = items;
    }

    int indexOf(int item) throws ValueNotFoundException {
        return search(item, 0, items.size() - 1);
    }

    int search(int item, int lower, int higher) throws ValueNotFoundException {
        if(lower > higher) {
            throw new ValueNotFoundException("Value not in array");
        }

        int middle = (higher + lower) / 2;
        int value = items.get(middle);

        if (value == item) {
            return middle;
        } else if (value >= item) {
            return search(item, lower, middle - 1);
        } else {
            return search(item, middle + 1, higher);
        }
    }
}
