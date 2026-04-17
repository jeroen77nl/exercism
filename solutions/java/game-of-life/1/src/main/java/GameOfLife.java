class GameOfLife {

    private static final int DEAD = 0;
    private static final int ALIVE = 1;

    public int[][] tick(int[][] matrix) {
        int rows = matrix.length;
        if (rows == 0) return matrix;

        int cols = matrix[0].length;
        if (cols == 0) return matrix;

        int[][] newMatrix = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                newMatrix[r][c] = deadOrAlive(matrix, r, c);
            }
        }

        return newMatrix;
    }

    private int deadOrAlive(int[][] matrix, int r, int c) {
        int liveN = liveNeighbours(matrix, r, c);
        if (matrix[r][c] == ALIVE && (liveN == 2 || liveN == 3)) {
            return ALIVE;
        }
        return matrix[r][c] == DEAD && liveN == 3 ? ALIVE : DEAD;
    }

    private int liveNeighbours(int[][] matrix, int r, int c) {
        int liveCells = 0;

        int rMin = Math.max(r - 1, 0);
        int rMax = Math.min(r + 1, matrix.length - 1);
        int cMin = Math.max(c - 1, 0);
        int cMax = Math.min(c + 1, matrix[0].length - 1);

        for (int ri = rMin; ri <= rMax; ri++) {
            for (int ci = cMin; ci <= cMax; ci++) {
                if (!(r == ri && c == ci)) {
                    if (matrix[ri][ci] == ALIVE) {
                        liveCells++;
                    }
                }
            }
        }

        return liveCells;
    }
}
