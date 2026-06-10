import java.util.ArrayList;
import java.util.List;

class RomanNumerals {
    private final int number;

    record LetterValue(int v, String s) {}

    List<LetterValue> list = new ArrayList<>();

    RomanNumerals(int number) {
        this.number = number;
        initList();
    }

    private void initList() {
        list.add(new LetterValue(1000, "M"));
        list.add(new LetterValue(900, "CM"));
        list.add(new LetterValue(500, "D"));
        list.add(new LetterValue(400, "CD"));
        list.add(new LetterValue(100, "C"));
        list.add(new LetterValue(90, "XC"));
        list.add(new LetterValue(50, "L"));
        list.add(new LetterValue(40, "XL"));
        list.add(new LetterValue(10, "X"));
        list.add(new LetterValue(9, "IX"));
        list.add(new LetterValue(5, "V"));
        list.add(new LetterValue(4, "IV"));
        list.add(new LetterValue(1, "I"));
    }

    String getRomanNumeral() {
        StringBuilder result = new StringBuilder();
        int n = number;
        int i = 0;
        while (n > 0) {
            if (n >= list.get(i).v()) {
                result.append(list.get(i).s());
                n -= list.get(i).v();
            } else {
                i++;
            }
        }

        return result.toString();
    }
}
