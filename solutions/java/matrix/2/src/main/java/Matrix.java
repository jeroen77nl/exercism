
class Matrix {

    private final int[][] matrix;

    Matrix(String matrixAsString) {
        if (matrixAsString == null || matrixAsString.isBlank()) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        matrix = fillMatrix(matrixAsString);
        validateMatrix();
    }

    int[] getRow(int rowNumber) {
        if (rowNumber < 1 || rowNumber > matrix.length) {
            throw new IllegalArgumentException("Invalid row number");
        }

        int rowIndex = rowNumber - 1;
        return matrix[rowIndex].clone();
    }

    int[] getColumn(int columnNumber) {
        if (columnNumber < 1 || columnNumber > matrix[0].length) {
            throw new IllegalArgumentException("Invalid column number");
        }

        int columnIndex = columnNumber - 1;
        int[] column = new int[matrix.length];

        for (int r = 0; r < matrix.length; r++) {
            column[r] = matrix[r][columnIndex];
        }

        return column;
    }

    private int[][] fillMatrix(String matrixAsString) {
        int[][] matrix;
        String[] rows = matrixAsString.split("\n");
        matrix = new int[rows.length][];
        for (int r = 0; r < rows.length; r++) {
            String[] numbersInRow = rows[r].trim().split("\\s+");
            matrix[r] = new int[numbersInRow.length];
            for (int c = 0; c < numbersInRow.length; c++) {
                matrix[r][c] = Integer.parseInt(numbersInRow[c]);
            }
        }
        return matrix;
    }

    private void validateMatrix() {
        int expectedLength = matrix[0].length;
        for (int r = 1; r < matrix.length; r++) {
            if (matrix[r].length != expectedLength) {
                throw new IllegalArgumentException("Irregular matrix");
            }
        }
    }
}
