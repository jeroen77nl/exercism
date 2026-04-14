import java.util.ArrayList;
import java.util.List;

class PrimeCalculator {

    private final List<Integer> primes = new ArrayList<>();

    int nth(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("nth must be a positive number");
        }

        primes.clear();
        primes.add(2);
        while (primes.size() < n) {
            calcNextPrime();
        }

        return primes.getLast();
    }

    private void calcNextPrime() {
        int candidate = primes.getLast() + 1;

        while (!isPrime(candidate)) {
            candidate++;
        }

        primes.add(candidate);
    }

    private boolean isPrime(int candidate) {
        int limit = (int) Math.sqrt(candidate);

        for (int p : primes) {
            if (p > limit) break;
            if (candidate % p == 0) return false;
        }
        return true;
    }
}
