import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class ParallelLetterFrequency {

    List<String> texts;
    ConcurrentMap<Character,Integer> lettersCount;

    ParallelLetterFrequency(String[] texts) {
        this.texts = List.of(texts);
        lettersCount = new ConcurrentHashMap<>();
    }

    Map<Character, Integer> countLetters() {
        if (!lettersCount.isEmpty() || texts.isEmpty()){
            return lettersCount;
        }
        texts.parallelStream().forEach(text ->{
            for(char c: text.toLowerCase().toCharArray()){
                if(Character.isAlphabetic(c)) {
                    lettersCount.merge(c, 1, Integer::sum);
                }
            }
        });
        return lettersCount;
    }
}