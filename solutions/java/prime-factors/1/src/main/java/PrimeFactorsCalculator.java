import java.util.ArrayList;
import java.util.List;

class PrimeFactorsCalculator {

    List<Long> calculatePrimeFactorsOf(long number) {
        List<Long> result = new ArrayList<>();

        long candidate = 2;

        while (candidate * candidate <= number) {
            while (number % candidate == 0) {
                result.add(candidate);
                number /= candidate;
            }
            candidate++;
        }

        if (number > 1) {
            result.add(number);
        }

        return result;
    }

}