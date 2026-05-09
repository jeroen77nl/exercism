import java.util.ArrayList;
import java.util.List;

class FlowerFieldBoard {

    private final char[][] board;

    FlowerFieldBoard(List<String> boardRows) {

        char[][] inBoard = new char[boardRows.size()][];

        for (int i = 0; i < boardRows.size(); i++) {
            inBoard[i] = boardRows.get(i).toCharArray();
        }

        board = inBoard.clone();
    }

    List<String> withNumbers() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == ' ') {
                    int count = countNearbyFlowers(r, c);
                    if (count > 0) {
                        char countChar = (char) (count + '0');
                        board[r][c] = countChar;
                    }
                }
            }
        }

        return convertBoardToList();
    }

    private int countNearbyFlowers(int row, int col) {
        final int[][] deltas = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 0}, {0, 1},
                {1,-1}, {1,0}, {1, 1}
        };

        int count = 0;

        for (int[] delta : deltas) {
            int r = row + delta[0];
            int c = col + delta[1];
            if (r >= 0 && r < board.length && c >= 0 && c < board[r].length) {
                if (r == row && c == col)
                    continue; // skip center-cell
                if (board[r][c] == '*')
                    count++;
            }
        }

        return count;
    }

    private List<String> convertBoardToList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            result.add(String.copyValueOf(board[i]));
        }
        return result;
    }

}