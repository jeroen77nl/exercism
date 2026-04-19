import java.util.List;

class Knapsack {

    int maximumValue(int maximumWeight, List<Item> items) {
        return maxValueIter(items, 0, maximumWeight);
    }

    int maxValueIter(List<Item> items, int i, int remainingWeight) {
        if (i >= items.size()) {
            return 0;
        }
        Item item = items.get(i);

        int valueWithoutItem = maxValueIter(items, i + 1, remainingWeight);

        int valueWithItem = 0;
        if (item.weight <= remainingWeight) {
            valueWithItem = item.value +
                    maxValueIter(items, i + 1, remainingWeight - item.weight);
        }

        return Math.max(valueWithItem, valueWithoutItem);    }
}