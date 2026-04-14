import java.util.*;
import java.util.stream.Collectors;

class BookStore {

    private record PricingState(List<Integer> groupedCounts, int largestAllowedSetSize) {}

    private static final int BOOK_PRICE_CENTS = 800;

    private static final Map<Integer, Integer> DISCOUNT_PERCENTAGES = Map.of(
            1, 0,
            2, 5,
            3, 10,
            4, 20,
            5, 25
    );

    private final Map<PricingState, Integer> memo = new HashMap<>();

    double calculateBasketCost(List<Integer> books) {
        return lowestPrice(groupedBookCounts(books), 5) / 100.0;
    }

    private int lowestPrice(List<Integer> groupedCounts, int largestAllowedSetSize) {
        if (groupedCounts.isEmpty()) {
            return 0;
        }

        if (largestAllowedSetSize == 0) {
            return Integer.MAX_VALUE;
        }

        PricingState state = new PricingState(groupedCounts, largestAllowedSetSize);
        Integer cached = memo.get(state);
        if (cached != null) {
            return cached;
        }

        /*
        Calculation 1: include largestAllowedSetSize
        End up with Integer.MAX_VALUE if set is not possible
         */
        int priceUsingThisSetSize = Integer.MAX_VALUE;

        if (canTakeSetOfSize(groupedCounts, largestAllowedSetSize)) {
            List<Integer> remainingCounts =
                    removeOneBookFromEachGroup(groupedCounts, largestAllowedSetSize);

            int remainingPrice = lowestPrice(remainingCounts, largestAllowedSetSize);

            if (remainingPrice != Integer.MAX_VALUE) {
                priceUsingThisSetSize =
                        priceOfSet(largestAllowedSetSize) + remainingPrice;
            }
        }

        /*
        Calculation 2: exclude largestAllowedSetSize from the set
        End up with Integer.MAX_VALUE if set is not possible
         */
        int priceUsingSmallerSet =
                lowestPrice(groupedCounts, largestAllowedSetSize - 1);

        int bestPrice = Math.min(priceUsingThisSetSize, priceUsingSmallerSet);
        memo.put(state, bestPrice);
        return bestPrice;
    }

    private boolean canTakeSetOfSize(List<Integer> groupedCounts, int setSize) {
        return setSize <= groupedCounts.size();
    }

    private List<Integer> removeOneBookFromEachGroup(List<Integer> groupedCounts, int setSize) {
        List<Integer> remainingCounts = new ArrayList<>(groupedCounts);

        for (int i = 0; i < setSize; i++) {
            remainingCounts.set(i, remainingCounts.get(i) - 1);
        }

        remainingCounts.removeIf(count -> count == 0);
        remainingCounts.sort(Comparator.reverseOrder());

        return remainingCounts;
    }

    private int priceOfSet(int setSize) {
        int discountPercentage = DISCOUNT_PERCENTAGES.get(setSize);
        return BOOK_PRICE_CENTS * setSize * (100 - discountPercentage) / 100;
    }

    /*
     * Example:
     * [1,1,3,4,4,4,5] -> [3,2,1,1]
     *
     * The list represents how many copies exist per distinct title,
     * sorted descending so equivalent basket states normalize to one form.
     */
    private List<Integer> groupedBookCounts(List<Integer> books) {
        int[] countsPerTitle = new int[5];

        for (int book : books) {
            validateBookId(book);
            countsPerTitle[book - 1]++;
        }

        return Arrays.stream(countsPerTitle)
                .filter(count -> count > 0)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void validateBookId(int book) {
        if (book < 1 || book > 5) {
            throw new IllegalArgumentException("Book id must be between 1 and 5");
        }
    }
}