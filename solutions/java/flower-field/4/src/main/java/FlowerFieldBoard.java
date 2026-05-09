import java.util.ArrayList;
import java.util.List;

class FlowerFieldBoard {

    private final char[][] board;

    FlowerFieldBoard(List<String> boardRows) {

        char[][] inBoard = new char[boardRows.size()][];

        for (int i = 0; i < boardRows.size(); i++) {
            inBoard[i] = boardRows.get(i).toCharArray();
        }

        board = inBoard;
    }

    List<String> withNumbers() {
        char[][] result = copyBoard();

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == ' ') {
                    int count = countNearbyFlowers(r, c);
                    if (count > 0) {
                        result[r][c] = Character.forDigit(count, 10);
                    }
                }
            }
        }

        return convertBoardToList(result);
    }

    private char[][] copyBoard() {
        char[][] result = new char[board.length][];

        for (int r = 0; r < board.length; r++) {
            result[r] = board[r].clone();
        }

        return result;
    }

    private int countNearbyFlowers(int row, int col) {

        int count = 0;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0)
                    continue;

                int r = row + dr;
                int c = col + dc;

                if (isInBoard(r, c)) {
                    if (board[r][c] == '*')
                        count++;
                }
            }
        }
        return count;
    }

    private boolean isInBoard(int r, int c) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }

    private static List<String> convertBoardToList(char[][] inBoard) {
        List<String> result = new ArrayList<>();
        for (char[] row : inBoard) {
            result.add(String.valueOf(row));
        }
        return result;
    }

}