class ComplexNumber {

    private double a;
    private double b;

    ComplexNumber(double real, double imaginary) {
        a = real;
        b = imaginary;
    }

    double getReal() {
        return a;
    }

    double getImaginary() {
        return b;
    }

    double abs() {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.a + other.a, this.b + other.b);
    }

    ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.a - other.a, this.b - other.b);
    }

    ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber(
                this.a * other.a - this.b * other.b,
                this.b * other.a + this.a * other.b
        );
    }

    ComplexNumber divide(ComplexNumber other) {
        double denominator = Math.pow(other.a, 2) + Math.pow(other.b, 2);
        return new ComplexNumber(
                (a * other.a + b * other.b) / denominator,
                +(b * other.a - a * other.b) / denominator
        );
    }

    ComplexNumber conjugate() {
        return new ComplexNumber(a, -b);
    }

    ComplexNumber exponentialOf() {
        return new ComplexNumber(
                Math.pow(Math.E, a) * Math.cos(b),
                Math.pow(Math.E, a) * Math.sin(b)
        );
    }
}