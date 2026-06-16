import java.util.Objects;

class Rational {

    private final int numerator;
    private final int denominator;

    Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }

        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }

        if (numerator == 0) {
            denominator = 1;
        } else {
            int gcd = gcd(numerator, denominator);
            numerator /= gcd;
            denominator /= gcd;
        }

        this.numerator = numerator;
        this.denominator = denominator;
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
        return Math.pow(exponent,
                (double) numerator / denominator);
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
