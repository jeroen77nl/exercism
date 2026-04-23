import java.util.Arrays;
import java.util.Scanner;

class RunLengthEncoding {

    String encode(String data) {
        StringBuilder result = new StringBuilder();

        int startPos = 0;
        while (startPos < data.length()) {
            int count = countSame(data, startPos);
            if (count >= 2) {
                result.append(count);
            }
            result.append(data.charAt(startPos));
            startPos += count;
        }

        return result.toString();
    }

    private int countSame(String data, int startPos) {
        int count = 1;
        for (int i = startPos + 1; i < data.length(); i++) {
            if (data.charAt(i) == data.charAt(i-1)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    String decode(String data) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < data.length()) {
            int number = 0;

            while (i < data.length() && Character.isDigit(data.charAt(i))) {
                number = number * 10 + (data.charAt(i) - '0');
                i++;
            }

            if (number == 0) {
                number = 1;
            }

            char c = data.charAt(i);
            i++;
            result.append(fullLengthString(c, number));
        }

        return result.toString();
    }

    private String fullLengthString(char c, int number) {
        char[] chars = new char[number];
        Arrays.fill(chars, c);
        return new String(chars);
    }
}