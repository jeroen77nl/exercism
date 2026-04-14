import java.util.ArrayList;
import java.util.List;

class DiamondPrinter {

    List<String> printToList(char highestLetter) {
        List<String> diamond = new ArrayList<>();
        for (char c = 'A'; c <= highestLetter; c++) {
            diamond.add(printLine(c, highestLetter));
        }
        for (char c = (char) (highestLetter - 1); c >= 'A'; c--) {
            diamond.add(printLine(c, highestLetter));
        }
        return diamond;
    }

    private String printLine(char letter, char highestLetter) {
        int sideMargin = highestLetter - letter;

        if (letter == 'A') {
            return spaces(sideMargin) + letter + spaces(sideMargin);
        } else {
            int middleMargin = (letter - 'A') * 2 - 1;
            return spaces(sideMargin) + letter +
                    spaces(middleMargin) + letter +
                    spaces(sideMargin);
        }
    }

    private String spaces(int aantal) {
        StringBuilder sb = new StringBuilder();
        sb.repeat(" ", aantal);
        return sb.toString();
    }
}
