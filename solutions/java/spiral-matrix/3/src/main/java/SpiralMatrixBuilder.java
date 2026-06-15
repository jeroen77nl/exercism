class SpiralMatrixBuilder {

    enum Direction {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        final int dRow;
        final int dCol;

        Direction(int dRow, int dCol) {
            this.dRow = dRow;
            this.dCol = dCol;
        }

        Direction next() {
            return values()[(ordinal() + 1) % 4];
        }
    }

    record Position(int count, int row, int col, Direction direction) {
    }

    int[][] buildMatrixOfSize(int size) {
        int[][] result = new int[size][size];

        if (size == 0) {
            return result;
        }

        Position pos = new Position(1, 0, 0, Direction.RIGHT);

        while (true) {
            result[pos.row][pos.col] = pos.count;

            Position next = move(pos, result);
            if (next == pos) {
                break;
            }
            pos = next;
        }

        return result;
    }

    private Position move(Position pos, int[][] result) {
        int row = pos.row;
        int col = pos.col;
        Direction direction = pos.direction;

        row += direction.dRow;
        col += direction.dCol;
        if (isValidPos(row, col, result)) {
            return new Position(pos.count + 1, row, col, direction);
        }

        direction = direction.next();
        row = pos.row + direction.dRow;
        col = pos.col + direction.dCol;
        if (isValidPos(row, col, result)) {
            return new Position(pos.count + 1, row, col, direction);
        }

        return pos;
    }

    private boolean isValidPos(int row, int col, int[][] result) {
        if (row < 0 || row >= result.length || col < 0 || col >= result[0].length) {
            return false;
        }

        return result[row][col] == 0;
    }
}
