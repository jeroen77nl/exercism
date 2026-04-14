import java.util.Arrays;
import java.util.List;

class ReverseString {

    String reverse(String inputString) {
        char[] arr = inputString.toCharArray();

        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            swap(arr, i, j);
        }

        return new String(arr);
    }

    private static void swap(char[] arr, int p, int q) {
        char temp = arr[p];
        arr[p] = arr[q];
        arr[q] = temp;
    }
}
