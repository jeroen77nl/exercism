import java.util.*;

class StateOfTicTacToe {

    private static final int N = 3;
    private final CellState[][] game = new CellState[N][N];

    public GameState determineState(String[] board) {
        parseBoardToGame(board);

        int xCount = 0;
        int oCount = 0;

        for (var row : game) {
            for (var cell : row) {
                if (cell == CellState.X) xCount++;
                if (cell == CellState.O) oCount++;
            }
        }

        validateTurnOrder(xCount, oCount);

        List<List<Point>> winningLines = getWinningLines();

        validateWinners(winningLines, xCount, oCount);

        if (!winningLines.isEmpty()) return GameState.WIN;

        if (isComplete()) return GameState.DRAW;

        return GameState.ONGOING;
    }

    private void parseBoardToGame(String[] board) {
        if (board.length != N) throw new IllegalArgumentException();

        for (int r = 0; r < N; r++) {
            if (board[r].length() != N) throw new IllegalArgumentException();

            for (int c = 0; c < N; c++) {
                char ch = board[r].charAt(c);
                game[r][c] = switch (ch) {
                    case 'X' -> CellState.X;
                    case 'O' -> CellState.O;
                    case ' ' -> CellState.EMPTY;
                    default -> throw new IllegalArgumentException();
                };
            }
        }
    }

    private void validateTurnOrder(int x, int o) {
        if (o > x) throw new IllegalArgumentException("Wrong turn order: O started");
        if (x - o > 1) throw new IllegalArgumentException("Wrong turn order: X went twice");
    }

    private void validateWinners(List<List<Point>> lines, int x, int o) {
        if (lines.isEmpty()) return;

        CellState winner = getWinner(lines.getFirst());

        // all lines must belong to same player
        for (var line : lines) {
            if (getWinner(line) != winner) {
                throw new IllegalArgumentException("Impossible board: game should have ended after the game was won");
            }
        }

        // winner must match turn count
        if (winner == CellState.X && x == o) {
            throw new IllegalArgumentException("X cannot win when O just played");
        }
        if (winner == CellState.O && x > o) {
            throw new IllegalArgumentException("O cannot win when X just played");
        }

        // all winning lines must intersect (same last move)
        Set<Point> intersection = new HashSet<>(lines.getFirst());

        for (var line : lines) {
            intersection.retainAll(line);
        }

        if (intersection.isEmpty()) {
            throw new IllegalArgumentException("Winning lines do not intersect");
        }
    }

    private List<List<Point>> getWinningLines() {
        List<List<Point>> result = new ArrayList<>();

        // rows
        for (int r = 0; r < N; r++) {
            if (same(game[r][0], game[r][1], game[r][2])) {
                result.add(List.of(new Point(r,0), new Point(r,1), new Point(r,2)));
            }
        }

        // cols
        for (int c = 0; c < N; c++) {
            if (same(game[0][c], game[1][c], game[2][c])) {
                result.add(List.of(new Point(0,c), new Point(1,c), new Point(2,c)));
            }
        }

        // diagonals
        if (same(game[0][0], game[1][1], game[2][2])) {
            result.add(List.of(new Point(0,0), new Point(1,1), new Point(2,2)));
        }

        if (same(game[0][2], game[1][1], game[2][0])) {
            result.add(List.of(new Point(0,2), new Point(1,1), new Point(2,0)));
        }

        return result;
    }

    private boolean same(CellState a, CellState b, CellState c) {
        return a != CellState.EMPTY && a == b && b == c;
    }

    private CellState getWinner(List<Point> line) {
        Point p = line.getFirst();
        return game[p.r][p.c];
    }

    private boolean isComplete() {
        for (var row : game) {
            for (var cell : row) {
                if (cell == CellState.EMPTY) return false;
            }
        }
        return true;
    }

    private static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point p)) return false;
            return r == p.r && c == p.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }
}