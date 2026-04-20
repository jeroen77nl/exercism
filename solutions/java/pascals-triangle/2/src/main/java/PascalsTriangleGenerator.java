class PascalsTriangleGenerator {

    int[][] generateTriangle(int rows) {
        int[][] result = new int[rows][];
        if (rows == 0) return result;

        result[0] = new int[] { 1 };

        for (int i = 1; i < rows; i++) {
            int[] newRow = new int[i+1];
            int[] prevRow = result[i-1];

            newRow[0] = 1;
            newRow[i] = 1;

            for (int j = 1; j < prevRow.length; j++) {
                newRow[j] = prevRow[j-1] + prevRow[j];
            }

            result[i] = newRow;
        }

        return result;
    }
}