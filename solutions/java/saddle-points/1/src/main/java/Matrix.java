import java.util.*;

class Matrix {

    private final int[][] matrix;

    Matrix(List<List<Integer>> values) {
        int rows = values.size();
        if (rows == 0) {
            matrix = null;
        } else {
            int cols = values.getFirst().size();

            matrix = new int[rows][cols];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    matrix[r][c] = values.get(r).get(c);
                }
            }
        }
    }

    Set<MatrixCoordinate> getSaddlePoints() {
        if (matrix == null) {
            return Set.of();
        }

        List<List<Integer>> tallestPerRow = new ArrayList<>();
        for (int r = 0; r < matrix.length; r++) {
            int max = matrix[r][0];
            for (int c = 1; c < matrix[r].length; c++) {
                if (matrix[r][c] > max) {
                    max = matrix[r][c];
                }
            }
            for (int c = 0; c < matrix[r].length; c++) {
                if (matrix[r][c] == max) {
                    tallestPerRow.add(List.of(r, c));
                }
            }
        }

        List<List<Integer>> shortestPerColumn = new ArrayList<>();
        for (int c = 0; c < matrix[0].length; c++) {
            int min = matrix[0][c];
            for (int r = 1; r < matrix.length; r++) {
                if (matrix[r][c] < min) {
                    min = matrix[r][c];
                }
            }
            for (int r = 0; r < matrix.length; r++) {
                if (matrix[r][c] == min) {
                    shortestPerColumn.add(List.of(r, c));
                }
            }
        }

        Set<MatrixCoordinate> coordinates = new HashSet<>();
        for (List<Integer> tallPoint : tallestPerRow) {
            if (shortestPerColumn.contains(tallPoint)) {
                coordinates.add(new MatrixCoordinate(
                        tallPoint.getFirst() + 1,
                        tallPoint.getLast() + 1));
            }
        }
        return coordinates;
    }
}
