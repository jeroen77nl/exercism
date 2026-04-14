import java.util.ArrayList;
import java.util.List;

class Sieve {

    private final List<Integer> primes;

    Sieve(int maxPrime) {
        primes = new ArrayList<>();

        boolean[] isComposite = new boolean[maxPrime + 1];

        for (int p = 2; p <= maxPrime; p++) {
            if (isComposite[p]) continue;

            primes.add(p);

            if ((long) p * p <= maxPrime) {
                for (int j = p * p; j <= maxPrime; j += p) {
                    isComposite[j] = true;
                }
            }
        }
    }

    List<Integer> getPrimes() {
        return new ArrayList<>(primes);
    }
}