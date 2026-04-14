import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Sieve {

    private final List<Integer> primes;

    Sieve(int maxPrime) {
        primes = new ArrayList<>(maxPrime / 2);
        if (maxPrime <= 1) return;

        byte[] numbers = new byte[maxPrime + 1];
        Arrays.fill(numbers, (byte) 0);

        // mark values 0, 1, 2 as processed
        numbers[0] = 1;
        numbers[1] = 1;
        numbers[2] = 1;
        // 2 is first prime
        primes.add(2);

        do {
            int prime = primes.getLast();
            for (int j = prime * prime; j <= maxPrime; j += prime) {
                if (numbers[j] == 0) {
                    numbers[j] = 1;
                }
            }
            // navigate to first unmarked number starting after last prime
            // --> next prime
            int i;
            for (i = prime + 1; i <= maxPrime && numbers[i] == 1; i++) {
            }
            if (i > maxPrime)
                break;
            primes.add(i);
        } while (true);
    }

    List<Integer> getPrimes() {
        return new ArrayList<>(primes);
    }
}
