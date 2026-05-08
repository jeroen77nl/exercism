import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Etl {
    Map<String, Integer> transform(Map<Integer, List<String>> valuesWithLetters) {

        Map<String, Integer> result = new HashMap<>();

        for (var valueWithLetters : valuesWithLetters.entrySet()) {
            int value = valueWithLetters.getKey();
            List<String> letters = valueWithLetters.getValue();
            for (String letter : letters) {
                result.put(letter.toLowerCase(), value);
            }
        }

        return result;
    }
}
