public class AffineCipher {
    private static final int M = 26;

    public String encode(String text, int a, int b) {
        if (!coPrime(a, M)) {
            throw new IllegalArgumentException("Error: keyA and alphabet size must be coprime.");
        }

        b = mod(b, M);
        text = text.toLowerCase();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char e;
            if (Character.isDigit(c)) {
                e = c;
            } else if (Character.isLetter(c)) {
                e = E(c, a, b);
            } else {
                continue;
            }

            result.append(e);
        }

        result = spaceText(result);
        return result.toString();
    }

    public String decode(String text, int a, int b) {
        text = text.toLowerCase();
        b = mod(b, M);
        int mmiA = modInverse(a);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char e = text.charAt(i);

            char c;
            if (Character.isDigit(e)) {
                c = e;
            } else if (e >= 'a' && e <= 'z') {
                c = D(e, mmiA, b);
            } else {
                continue;
            }

            result.append(c);
        }

        return result.toString();
    }

    private StringBuilder spaceText(StringBuilder text) {
        StringBuilder result = new StringBuilder();
        int textSize = text.length();

        for (int i = 0; i < textSize; i++) {
            if (i > 0 && i % 5 == 0 && i < textSize - 1) {
                result.append(' ');
            }
            result.append(text.charAt(i));
        }

        return result;
    }

    private char E(char c, int a, int b) {
        int x = c - 'a';
        return (char) (mod(a * x + b, M) + 'a');
    }

    private static boolean coPrime(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a == 1;
    }

    private char D(char e, int mmiA, int b) {
        int y = e - 'a';
        return (char) (mod(mmiA * (y - b), M) + 'a');
    }

    // Returns gcd(a, b) and computes x, y such that ax + by = gcd(a, b)
    private static int extendedGcd(int a, int b, int[] xy) {
        if (b == 0) {
            xy[0] = 1; // x
            xy[1] = 0; // y
            return a;
        }

        int[] xy1 = new int[2];
        int gcd = extendedGcd(b, a % b, xy1);

        xy[0] = xy1[1];
        xy[1] = xy1[0] - (a / b) * xy1[1];

        return gcd;
    }

    // Returns modular inverse of a mod m, or throws if it doesn't exist
    private static int modInverse(int a) {
        int[] xy = new int[2];
        int gcd = extendedGcd(a, M, xy);

        if (gcd != 1) {
            throw new IllegalArgumentException("Error: keyA and alphabet size must be coprime.");
        }

        // Ensure positive result
        return (xy[0] % M + M) % M;
    }

    private static int mod(int a, int b) {
        return (a % b + b) % b;
    }
}