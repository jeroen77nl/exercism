import java.util.ArrayList;
import java.util.List;

public class Transpose {
    public String transpose(String toTranspose) {
        if (toTranspose.isEmpty()) {
            return "";
        }

        char[][] in;
        String[] rows = toTranspose.split("\n");

        int maxColumns = 0;
        for (int r = 0; r < rows.length; r++) {
            maxColumns = Math.max(maxColumns, rows[r].length());
        }

        in = new char[rows.length][maxColumns];
        for (int r = 0; r < rows.length; r++) {
            for (int c = 0; c < rows[r].length(); c++) {
                in[r][c] = rows[r].charAt(c);
            }
            for (int c = rows[r].length(); c < maxColumns; c++) {
                in[r][c] = '\0';
            }
        }

        char[][] out = new char[maxColumns][rows.length];
        for (int r = 0; r < rows.length; r++) {
            for (int c = 0; c < maxColumns; c++) {
                out[c][r] = in[r][c];
            }
        }

        List<String> result = new ArrayList<>();
        for (int r = 0; r < out.length; r++) {
            String s = String.copyValueOf(out[r]);
            if (!s.trim().isEmpty()) {
                s = rtrim(s);
            }
            s = ltrim(s);
            result.add(s);
        }

        return String.join("\n", result);
    }

    private static String rtrim(String s) {
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == '\0') {
            i--;
        }
        return s.substring(0, i + 1);
    }

    private static String ltrim(String s) {
        return s.replace('\0', ' ');
    }
}
