
class Matrix {

    private final int[][] matrix;

    Matrix(String matrixAsString) {

        String[] rows = matrixAsString.split("\n");
        matrix = new int[rows.length][];
        for (int r = 0; r < rows.length; r++) {
            String[] numbersInRow = rows[r].split(" ");
            matrix[r] = new int[numbersInRow.length];
            for (int c = 0; c < numbersInRow.length; c++) {
                matrix[r][c] = Integer.parseInt(numbersInRow[c]);
            }
        }
    }

    int[] getRow(int rowNumber) {
        return matrix[rowNumber - 1];
    }

    int[] getColumn(int columnNumber) {
        int[] column =  new int[matrix.length];

        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                if (c == columnNumber - 1) {
                    column[r] = matrix[r][c];
                }
            }
        }

        return column;
    }
}
