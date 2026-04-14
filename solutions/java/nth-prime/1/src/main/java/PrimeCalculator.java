import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PrimeCalculator {

    private final List<Integer> primes = new ArrayList<>();

    int nth(int nth) {
        if (nth < 1) {
            throw new IllegalArgumentException("nth must be a positive number");
        }

        primes.add(2);
        while (primes.size() < nth) {
            calcNextPrime();
        }

        return primes.getLast();
    }

    private void calcNextPrime() {
        int maybeNextPrime = primes.getLast() + 1;

        while (true) {
            boolean isAPrime = true;
            for (int earlierPrime : primes) {
                if (maybeNextPrime % earlierPrime == 0) {
                    isAPrime = false;
                    break;
                }
            }
            if (isAPrime) {
                primes.add(maybeNextPrime);
                return;
            } else {
                maybeNextPrime++;
            }
        }
    }
}
