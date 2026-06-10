import java.util.ArrayList;
import java.util.List;

class RomanNumerals {
    private final int number;

    record LetterValue(int decimal, String roman) {
    }

    List<LetterValue> letterValues = new ArrayList<>();

    RomanNumerals(int number) {
        this.number = number;
        initList();
    }

    private void initList() {
        letterValues.add(new LetterValue(1000, "M"));
        letterValues.add(new LetterValue(900, "CM"));
        letterValues.add(new LetterValue(500, "D"));
        letterValues.add(new LetterValue(400, "CD"));
        letterValues.add(new LetterValue(100, "C"));
        letterValues.add(new LetterValue(90, "XC"));
        letterValues.add(new LetterValue(50, "L"));
        letterValues.add(new LetterValue(40, "XL"));
        letterValues.add(new LetterValue(10, "X"));
        letterValues.add(new LetterValue(9, "IX"));
        letterValues.add(new LetterValue(5, "V"));
        letterValues.add(new LetterValue(4, "IV"));
        letterValues.add(new LetterValue(1, "I"));
    }

    String getRomanNumeral() {
        StringBuilder result = new StringBuilder();
        int n = number;
        for (LetterValue lv : letterValues) {
            while (n >= lv.decimal) {
                result.append(lv.roman);
                n -= lv.decimal;
            }
        }

        return result.toString();
    }
}
