import java.util.ArrayList;
import java.util.List;

class PythagoreanTriplet {

    private final int a;
    private final int b;
    private final int c;

    @Override
    public String toString() {
        return "PythagoreanTriplet{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    PythagoreanTriplet(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    static TripletListBuilder makeTripletsList() {
        return new TripletListBuilder();
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof PythagoreanTriplet that)) return false;
        return a == that.a && b == that.b && c == that.c;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        result = 31 * result + c;
        return result;
    }

    static class TripletListBuilder {

        private final List<PythagoreanTriplet> triplets = new ArrayList<>();
        private int sum = 0;
        private int maxFactor = 0;

        TripletListBuilder thatSumTo(int sum) {
            this.sum = sum;
            return this;
        }

        TripletListBuilder withFactorsLessThanOrEqualTo(int maxFactor) {
            this.maxFactor = maxFactor;
            return this;
        }

        List<PythagoreanTriplet> build() {
            if (maxFactor == 0)
                maxFactor = sum;

            for (int a = 1; a <= maxFactor; a++) {
                for (int b = a + 1; b <= maxFactor; b++) {
                    int c = sum - a - b;
                    if (c > maxFactor) continue;
                    if (c * c == a * a + b * b) {
                        triplets.add(new PythagoreanTriplet(a, b, c));
                    }
                }
            }
            return this.triplets;
        }

    }

}