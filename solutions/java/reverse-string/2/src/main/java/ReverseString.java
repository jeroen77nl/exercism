import java.util.Arrays;
import java.util.List;

class ReverseString {

    String reverse(String inputString) {
        char[] arr = inputString.toCharArray();

        int i = 0;
        int j = arr.length - 1;

        while (i < j) {
            swap(arr, i, j);
            i++;
            j--;
        }

        return new String(arr);
    }

    private static void swap(char[] arr, int p, int q) {
        char temp = arr[p];
        arr[p] = arr[q];
        arr[q] = temp;
    }
}
