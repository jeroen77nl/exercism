import java.util.*;

class Matrix {

    private final int[][] matrix;

    Matrix(List<List<Integer>> values) {
        int rows = values.size();
        if (rows == 0) {
            matrix = new int[0][0];
            return;
        }

        int cols = values.getFirst().size();
        if (cols == 0) {
            matrix = new int[0][0];
            return;
        }

        for (List<Integer> row : values) {
            if (row.size() != cols) {
                throw new IllegalArgumentException("Matrix must be rectangular");
            }
        }

        matrix = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                matrix[r][c] = values.get(r).get(c);
            }
        }
    }

    Set<MatrixCoordinate> getSaddlePoints() {
        int rows = matrix.length;
        if (rows == 0)
            return Set.of();

        int cols = matrix[0].length;
        if (cols == 0)
            return Set.of();

        Set<MatrixCoordinate> tallestPerRow = new HashSet<>();
        for (int r = 0; r < rows; r++) {
            int[] row = matrix[r];
            int max = row[0];
            for (int c = 1; c < row.length; c++) {
                max = Math.max(max, row[c]);
            }
            for (int c = 0; c < row.length; c++) {
                if (row[c] == max) {
                    tallestPerRow.add(
                            new MatrixCoordinate(r + 1, c + 1));
                }
            }
        }

        Set<MatrixCoordinate> shortestPerColumn = new HashSet<>();
        for (int c = 0; c < cols; c++) {
            int min = matrix[0][c];
            for (int r = 1; r < rows; r++) {
                min = Math.min(min, matrix[r][c]);
            }
            for (int r = 0; r < rows; r++) {
                if (matrix[r][c] == min) {
                    shortestPerColumn.add(new MatrixCoordinate(r + 1, c + 1));
                }
            }
        }

        tallestPerRow.retainAll(shortestPerColumn);
        return tallestPerRow;
    }
}
