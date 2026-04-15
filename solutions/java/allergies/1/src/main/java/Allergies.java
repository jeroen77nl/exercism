import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

class Allergies {
    private final Set<Allergen> allergens;

    Allergies(int score) {
        score = score & 0xFF; // same as % 256 but more idiomatic for bitmasks

        Set<Allergen> set = EnumSet.noneOf(Allergen.class);
        for (Allergen allergen : Allergen.values()) {
            if ((score & allergen.getScore()) != 0) {
                set.add(allergen);
            }
        }
        this.allergens = set;
    }

    boolean isAllergicTo(Allergen allergen) {
        return allergens.contains(allergen);
    }

    List<Allergen> getList() {
        return new ArrayList<>(allergens); // already in enum order
    }
}