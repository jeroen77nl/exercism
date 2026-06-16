import java.util.Objects;

class Rational {

    private int numerator;
    private int denominator;

    Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }

        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
        normalizeSign();
    }

    private void reduce() {
        if (numerator == 0) {
            this.denominator = 1;
        } else {
            int ggd = gcd(this.numerator, this.denominator);
            this.numerator /= ggd;
            this.denominator /= ggd;
        }
    }

    int getNumerator() {
        return this.numerator;
    }

    int getDenominator() {
        return this.denominator;
    }

    Rational add(Rational other) {
        return new Rational(
                this.numerator * other.denominator + this.denominator * other.numerator,
                this.denominator * other.denominator
        );
    }

    Rational subtract(Rational other) {
        return new Rational(
                this.numerator * other.denominator - this.denominator * other.numerator,
                this.denominator * other.denominator
        );
    }

    Rational multiply(Rational other) {
        return new Rational(
                this.numerator * other.numerator,
                this.denominator * other.denominator
        );
    }

    Rational divide(Rational other) {
        if (other.numerator == 0)
            throw new IllegalArgumentException("Cannot divide by zero");

        return new Rational(
                this.numerator * other.denominator,
                other.numerator * this.denominator
        );
    }

    Rational abs() {
        return new Rational(Math.abs(this.numerator), this.denominator);
    }

    Rational pow(int power) {
        if (power >= 0) {
            return new Rational(
                    Math.powExact(this.numerator, power),
                    Math.powExact(this.denominator, power)
            );
        } else {
            return new Rational(
                    Math.powExact(this.denominator, -power),
                    Math.powExact(this.numerator, -power)
            );
        }
    }

    double exp(double exponent) {
        double p = Math.pow(exponent, this.numerator);
        return Math.pow(p, 1.0 / this.denominator);

    }

    @Override
    public String toString() {
        return String.format("%d/%d", this.getNumerator(), this.getDenominator());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rational other) {
            return this.getNumerator() == other.getNumerator()
                    && this.getDenominator() == other.getDenominator();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getNumerator(), this.getDenominator());
    }

    private void normalizeSign() {
        if (numerator * denominator < 0) {
            numerator = -Math.abs(numerator);
        } else {
            numerator = Math.abs(numerator);
        }
        denominator = Math.abs(denominator);
    }

    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int rest = a % b;
            a = b;
            b = rest;
        }

        return a;
    }

}
