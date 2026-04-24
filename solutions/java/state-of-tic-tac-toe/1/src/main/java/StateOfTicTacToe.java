import java.util.ArrayList;
import java.util.List;

class StateOfTicTacToe {

    private static final int ROWS_COLS = 3;

    private final CellState[][] game = new CellState[3][3];

    public GameState determineState(String[] board) {
        boardToGame(board);
        checkOrder();

        if (winningColumn() || winningRows() || winningDiagonals()) {
            return GameState.WIN;
        }

        if (isComplete()) {
            return GameState.DRAW;
        }

        return GameState.ONGOING;
    }

    private boolean isComplete() {
        for (var row : game) {
            for (var state : row) {
                if (state == CellState.EMPTY)
                    return false;
            }
        }

        return true;
    }

    private void boardToGame(String[] board) {
        checkBoardDimensions(board);

        for (int r = 0; r < ROWS_COLS; r++) {
            for (int c = 0; c < ROWS_COLS; c++) {
                switch (board[r].charAt(c)) {
                    case 'X':
                        game[r][c] = CellState.X;
                        break;
                    case 'O':
                        game[r][c] = CellState.O;
                        break;
                    case ' ':
                        game[r][c] = CellState.EMPTY;
                        break;
                    default:
                        throw new IllegalArgumentException("Undefined cell value");
                }
            }
        }
    }

    private static void checkBoardDimensions(String[] board) {
        if (board.length != ROWS_COLS) throw new IllegalArgumentException("Wrong dimensions");
        for (String s : board) {
            if (s.length() != ROWS_COLS) {
                throw new IllegalArgumentException("Wrong dimensions");
            }
        }
    }

    private void checkOrder() {
        int x = 0;
        int o = 0;
        int empty = 0;
        for (var row : game) {
            for (var state : row) {
                switch (state) {
                    case CellState.X -> x++;
                    case CellState.O -> o++;
                    case CellState.EMPTY -> empty++;
                }
            }
        }

        if (x < o) {
            throw new IllegalArgumentException("Wrong turn order: O started");
        }
        if (x - o > 1) {
            throw new IllegalArgumentException("Wrong turn order: X went twice");
        }
    }

    private boolean winningColumn() {
        List<Integer> cols = new ArrayList<>();
        for (int c = 0; c < ROWS_COLS; c++) {
            if (columnWins(c)) {
                cols.add(c);
            }
        }

        if (cols.size() > 1) {
            throw new IllegalArgumentException("Impossible board: game should have ended after the game was won");
        }

        return (cols.size() == 1);
    }

    private boolean columnWins(int c) {
        return game[0][c] != CellState.EMPTY &&
                game[0][c] == game[1][c] &&
                game[1][c] == game[2][c];
    }

    private boolean winningRows() {
        List<Integer> rows = new ArrayList<>();
        for (int r = 0; r < ROWS_COLS; r++) {
            if (rowWins(r)) {
                rows.add(r);
            }
        }

        if (rows.size() > 1) {
            throw new IllegalArgumentException("Impossible board: game should have ended after the game was won");
        }

        return (rows.size() == 1);
    }

    private boolean rowWins(int r) {
        return game[r][0] != CellState.EMPTY &&
                game[r][0] == game[r][1] &&
                game[r][1] == game[r][2];
    }

    private boolean winningDiagonals() {
        return (game[0][0] != CellState.EMPTY &&
                game[0][0] == game[1][1] &&
                game[1][1] == game[2][2])
                ||
        (game[0][2] != CellState.EMPTY &&
                game[0][2] == game[1][1] &&
                game[1][1] == game[2][0]);
    }

}
