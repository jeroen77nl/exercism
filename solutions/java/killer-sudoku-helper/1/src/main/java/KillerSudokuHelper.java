import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KillerSudokuHelper {

    List<List<Integer>> combinationsInCage(int cageSum, int cageSize) {
        return combinationsInCage(cageSum, cageSize, List.of());
    }

    List<List<Integer>> combinationsInCage(int cageSum,
                                           int cageSize,
                                           List<Integer> excluded) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> excludedSet = excluded == null
                ? Set.of()
                : Set.copyOf(excluded);
        combinations(cageSum,
                cageSize,
                0,
                new ArrayList<>(),
                result,
                excludedSet);
        return result;
    }

    private void combinations(int targetSum,
                              int cageSize,
                              int currentSum,
                              List<Integer> candidates,
                              List<List<Integer>> result,
                              Set<Integer> excluded) {

        if (candidates.size() == cageSize) {
            if (currentSum == targetSum) {
                result.add(new ArrayList<>(candidates));
            }
            return;
        }

        if (currentSum >= targetSum) {
            return;
        }

        int start = candidates.isEmpty() ? 1 : candidates.getLast() + 1;

        for (int digit = start; digit <= 9; digit++) {
            if (excluded.contains(digit)) {
                continue;
            }

            candidates.add(digit);
            combinations(targetSum, cageSize, currentSum + digit, candidates, result, excluded);
            candidates.removeLast();
        }
    }
}