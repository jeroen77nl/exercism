class DifferenceOfSquaresCalculator {

    int computeSquareOfSumTo(int input) {
        int result = 0;
        for (int i = 1; i <= input; i++) {
            result += i;
        }
        return Math.powExact(result, 2);
    }

    int computeSumOfSquaresTo(int input) {
        int result = 0;
        for (int i = 1; i <= input; i++) {
            result += Math.powExact(i, 2);
        }
        return result;
    }

    int computeDifferenceOfSquares(int input) {
        return computeSquareOfSumTo(input) - computeSumOfSquaresTo(input);
    }

}
