class Matrix {

    private final int[][] matrix;

    Matrix(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }

        this.matrix = parse(input);
        validateRectangular();
    }

    int[] getRow(int rowNumber) {
        checkRow(rowNumber);
        return matrix[rowNumber - 1].clone();
    }

    int[] getColumn(int columnNumber) {
        checkColumn(columnNumber);

        return java.util.Arrays.stream(matrix)
                .mapToInt(row -> row[columnNumber - 1])
                .toArray();
    }

    private int[][] parse(String input) {
        return java.util.Arrays.stream(input.split("\n"))
                .map(this::parseRow)
                .toArray(int[][]::new);
    }

    private int[] parseRow(String row) {
        if (row.isBlank()) {
            throw new IllegalArgumentException("Empty row not allowed");
        }

        return java.util.Arrays.stream(row.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private void validateRectangular() {
        int expected = matrix[0].length;

        for (int r = 1; r < matrix.length; r++) {
            if (matrix[r].length != expected) {
                throw new IllegalArgumentException("Irregular matrix");
            }
        }
    }

    private void checkRow(int row) {
        if (row < 1 || row > matrix.length) {
            throw new IndexOutOfBoundsException("Invalid row");
        }
    }

    private void checkColumn(int col) {
        if (col < 1 || col > matrix[0].length) {
            throw new IndexOutOfBoundsException("Invalid column");
        }
    }
}