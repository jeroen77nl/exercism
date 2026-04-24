public class SquareRoot {
    public int squareRoot(int radicand) {
        if (radicand == 0) {
            return 0;
        }

        long candidate = radicand;

        while (candidate * candidate > radicand) {
            candidate = (candidate + radicand / candidate) / 2;
        }

        return (int)candidate;
    }
}
