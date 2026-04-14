import java.util.*;

class ProteinTranslator {

    private final static Map<String, String> map = new HashMap<>();

    static {
        map.put("AUG", "Methionine");
        map.put("UUU", "Phenylalanine");
        map.put("UUC", "Phenylalanine");
        map.put("UUA", "Leucine");
        map.put("UUG", "Leucine");
        map.put("UCU", "Serine");
        map.put("UCC", "Serine");
        map.put("UCA", "Serine");
        map.put("UCG", "Serine");
        map.put("UAU", "Tyrosine");
        map.put("UAC", "Tyrosine");
        map.put("UGU", "Cysteine");
        map.put("UGC", "Cysteine");
        map.put("UGG", "Tryptophan");
        map.put("UAA", "STOP");
        map.put("UAG", "STOP");
        map.put("UGA", "STOP");
    }

    List<String> translate(String rnaSequence) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < rnaSequence.length(); i += 3) {
            String codon;
            try {
                codon = rnaSequence.substring(i, i + 3);
            } catch (RuntimeException e) {
                throw new IllegalArgumentException("Invalid codon");
            }
            String aminoAcid = map.get(codon);
            if (aminoAcid == null) throw new IllegalArgumentException("Invalid codon");
            if (aminoAcid.equals("STOP")) break;
            result.add(aminoAcid);
        }

        return result;
    }
}
