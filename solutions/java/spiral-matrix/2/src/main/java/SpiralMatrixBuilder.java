class SpiralMatrixBuilder {

    enum Direction {
        NOORD, OOST, ZUID, WEST
    }

    record Position(int count, int row, int col, Direction direction) {
    }

    private int[][] result;

    int[][] buildMatrixOfSize(int size) {
        result = new int[size][size];

        if (size == 0) {
            return result;
        }

        Position pos = new Position(1, 0, 0, Direction.OOST);
        Position oldPos = null;

        while (pos != oldPos) {
            result[pos.row][pos.col] = pos.count;
            oldPos = pos;
            pos = move(pos);
        }

        return result;
    }

    private Position move(Position pos) {
        int row = pos.row;
        int col = pos.col;
        Direction direction = pos.direction;

        if (direction == Direction.NOORD) {
            row--;
            if (row < 0 || result[row][col] != 0) {
                row++;
                direction = Direction.OOST;
                col++;
                if (col >= result.length || result[row][col] != 0) {
                    return pos;
                }
            }
        } else if (direction == Direction.OOST) {
            col++;
            if (col >= result.length || result[row][col] != 0) {
                col--;
                direction = Direction.ZUID;
                row++;
                if (row >= result.length || result[row][col] != 0) {
                    return pos;
                }
            }
        } else if (direction == Direction.ZUID) {
            row++;
            if (row >= result.length || result[row][col] != 0) {
                row--;
                direction = Direction.WEST;
                col--;
                if (col < 0 || result[row][col] != 0) {
                    return pos;
                }
            }
        } else if (direction == Direction.WEST) {
            col--;
            if (col < 0 || result[row][col] != 0) {
                col++;
                direction = Direction.NOORD;
                row--;
                if (row < 0 || result[row][col] != 0) {
                    return pos;
                }
            }
        }

        return new Position(pos.count + 1, row, col, direction);
    }
}
