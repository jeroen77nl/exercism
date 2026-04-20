class PascalsTriangleGenerator {

    int[][] generateTriangle(int rows) {
        int[][] result = new int[rows][];
        if (rows == 0) return result;

        result[0] = new int[] { 1 };
        if (rows == 1) return result;

        for (int i = 1; i < rows; i++) {
            int[] newRow = new int[i+1];
            int[] previousRow = result[i-1];
            newRow[0] = 1;
            for (int j = 1; j < previousRow.length; j++) {
                newRow[j] = previousRow[j-1] + previousRow[j];
            }
            newRow[newRow.length-1] = 1;
            result[i] = newRow;
        }

        return result;
    }

}