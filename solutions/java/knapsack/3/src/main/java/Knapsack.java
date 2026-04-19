import java.util.Arrays;
import java.util.List;

class Knapsack {

    private int[][] dp;

    int maximumValue(int maximumWeight, List<Item> items) {
        dp = new int[items.size()][maximumWeight + 1];
        for (var row: dp) {
            Arrays.fill(row, -1);
        }
        return maxValueIter(items, 0, maximumWeight);
    }

    int maxValueIter(List<Item> items, int i, int remainingWeight) {
        if (i >= items.size()) {
            return 0;
        }
        if (dp[i][remainingWeight] != -1) {
            return dp[i][remainingWeight];
        }

        Item item = items.get(i);

        int valueWithoutItem = maxValueIter(items, i + 1, remainingWeight);

        int valueWithItem = 0;
        if (item.weight <= remainingWeight) {
            valueWithItem = item.value +
                    maxValueIter(items, i + 1, remainingWeight - item.weight);
        }

        int result = Math.max(valueWithItem, valueWithoutItem);
        dp[i][remainingWeight] = result;
        return result;
    }
}