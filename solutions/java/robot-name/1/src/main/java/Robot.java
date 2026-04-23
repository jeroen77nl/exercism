import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Robot {
    private static final Set<String> names = new HashSet<>();
    private static final Random random = new Random();

    private String name = "";

    String getName() {
        if (this.name.isEmpty()) {
            reset();
        }
        return this.name;
    }

    void reset() {
        do {
            name = "" + randomLetter() + randomLetter()
                    + randomDigit() + randomDigit() + randomDigit();
        } while (!names.add(name));
    }

    private char randomLetter() {
        return (char) (random.nextInt(26) + (int) 'A');
    }

    private char randomDigit() {
        return (char) (random.nextInt(10) + (int) '0');
    }
}