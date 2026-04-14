import java.util.Map;

class RnaTranscription {

    private final Map<Character, Character> DNA_TO_RNA = Map.of(
            'G', 'C',
            'C', 'G',
            'T', 'A',
            'A', 'U'
    );

    String transcribe(String dnaStrand) {
        char[] chars = dnaStrand.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            chars[i] = DNA_TO_RNA.getOrDefault(chars[i], chars[i]);
        }

        return new String(chars);
    }

}
