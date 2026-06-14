import java.util.ArrayList;
import java.util.List;

class ChangeCalculator {

    private final List<Integer> coins;

    private List<Integer> bestSolution = null;

    ChangeCalculator(List<Integer> currencyCoins) {
        this.coins = currencyCoins.stream()
                .sorted()
                .toList();
    }

    List<Integer> computeMostEfficientChange(int grandTotal) {
        bestSolution = null;

        if (grandTotal < 0) {
            throw new IllegalArgumentException("Negative totals are not allowed.");
        }

        if (grandTotal > 0 && grandTotal < coins.getFirst()) {
            throw new IllegalArgumentException("The total %d cannot be represented in the given currency.".formatted(grandTotal));
        }

        change(List.of(), grandTotal, 0);

        if (bestSolution == null) {
            throw new IllegalArgumentException("The total %d cannot be represented in the given currency.".formatted(grandTotal));
        }

        return bestSolution;
    }

    private void change(List<Integer> changes, int amountRemaining, int smallestCoinIndex) {

        if (amountRemaining < 0) {
            return;
        }

        if (amountRemaining == 0) {
            if (bestSolution == null || changes.size() < bestSolution.size()) {
                bestSolution = changes;
            }
            return;
        }

        if (bestSolution != null && changes.size() >= bestSolution.size()) {
            return;
        }

        for (int i = coins.size() - 1; i >= smallestCoinIndex; i--) {
            int coin = coins.get(i);
            if (coin > amountRemaining) {
                continue;
            }

            List<Integer> extended = new ArrayList<>(changes);
            extended.add(coin);
            change(List.copyOf(extended), amountRemaining - coin, i);
        }
    }

}
