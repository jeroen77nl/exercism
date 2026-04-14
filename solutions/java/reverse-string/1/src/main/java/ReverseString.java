import java.util.Arrays;
import java.util.List;

class ReverseString {

    String reverse(String inputString) {
        String[] arr = inputString.split("");
        int i = 0;
        int j = arr.length - 1;
        while (i < j) {
            String temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
            i++;
            j--;
        }

        return String.join("", arr); 
    }
  
}
