import java.math.BigInteger;

class Grains {

    BigInteger grainsOnSquare(final int square) {
        if (square < 1 || square > 64)
            throw new IllegalArgumentException("square must be between 1 and 64");

        BigInteger grainsOnSquare = BigInteger.valueOf(1);
        for (int i = 2; i <= square; i++) {
            grainsOnSquare = grainsOnSquare.multiply(BigInteger.valueOf(2));
        }
        return grainsOnSquare;
    }

    BigInteger grainsOnBoard() {
        BigInteger grainsOnBoard = BigInteger.ONE;
        BigInteger grainsOnSquare = BigInteger.ONE;
        for (int i = 2; i <= 64; i++) {
            grainsOnSquare = grainsOnSquare.multiply(BigInteger.valueOf(2));
            grainsOnBoard = grainsOnBoard.add(grainsOnSquare);
        }
        return grainsOnBoard;
    }

}
