import java.util.HashSet;
import java.util.Set;

class SumOfMultiples {

    private final int sum;

    SumOfMultiples(int number, int[] set) {
        Set<Integer> totalSet = new HashSet<>();

        for (Integer value : set) {
            if (value == 0) continue;
            for (int i = value; i < number; i += value) {
                totalSet.add(i);
            }
        }

        sum = totalSet.stream().mapToInt(Integer::intValue).sum();
    }

    int getSum() {
        return this.sum;
    }
}
