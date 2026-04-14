import java.util.Arrays;
import java.util.List;

class ReverseString {

    String reverse(String inputString) {
        char[] arr = inputString.toCharArray();
        int size = arr.length;

        for (int i=0; i < size / 2; i++) {
            swap(arr, i, size - 1 - i);
        }

        return new String(arr);
    }

    private static void swap(char[] arr, int p, int q) {
        char temp = arr[p];
        arr[p] = arr[q];
        arr[q] = temp;
    }
}
