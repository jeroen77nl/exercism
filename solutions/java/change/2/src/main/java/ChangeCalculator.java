import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class ChangeCalculator {

    private final List<Integer> coins;

    ChangeCalculator(List<Integer> currencyCoins) {
        this.coins = currencyCoins;
    }

    List<Integer> computeMostEfficientChange(int grandTotal) {
        return minCoins(grandTotal, this.coins);
    }

    private List<Integer> minCoins(int amount, List<Integer> coins) {

        if (amount < 0) {
            throw new IllegalArgumentException("Negative totals are not allowed.");
        }

        int[] dp = new int[amount + 1];
        int[] parent = new int[amount + 1];

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {

            for (int coin : coins) {

                if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {

                    int candidate = dp[i - coin] + 1;

                    if (candidate < dp[i]) {
                        dp[i] = candidate;
                        parent[i] = coin;
                    }
                }
            }
        }

        if (dp[amount] == Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "The total %d cannot be represented in the given currency."
                            .formatted(amount));
        }

        List<Integer> result = new ArrayList<>();

        int curr = amount;

        while (curr > 0) {
            int coin = parent[curr];
            result.add(coin);
            curr -= coin;
        }

        result.sort(Integer::compareTo);

        return result;
    }}
