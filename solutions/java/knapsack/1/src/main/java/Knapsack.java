import java.util.List;

class Knapsack {

    int maximumValue(int maximumWeight, List<Item> items) {
        return maxValueIter(
                maximumWeight,
                items,
                0,
                0,
                0
        );
    }

    int maxValueIter(int maxWeight, List<Item> items, int i, int weightAcc, int valueAcc) {
        if (i >= items.size()) {
            return valueAcc;
        }
        Item item = items.get(i);

        int valueWithItem = 0;
        if (item.weight + weightAcc <= maxWeight) {
            valueWithItem = maxValueIter(
                    maxWeight,
                    items,
                    i + 1,
                    weightAcc + item.weight,
                    valueAcc + item.value
            );
        }

        int valueWithoutItem = maxValueIter(
                maxWeight,
                items,
                i+1,
                weightAcc,
                valueAcc
        );

        return Math.max(valueWithItem, valueWithoutItem);
    }
/*
            new Item(5, 10),
            new Item(4, 40),
            new Item(6, 30),
            new Item(4, 50)

            maxW = 10

            0  1  2  3
            5           5  10
            5  4        9  50
            5  4  6    15  80 *
            5  4     4 13 100 *
            5     6    11  40 *
            5     6  4 15  90 *
            5        4  9  60
               4        4  40
               4  6    10  70
               4  6  4 14 120 *
               4     4  8  90
                  6     6  30
                  6  4 10  80

 */
}