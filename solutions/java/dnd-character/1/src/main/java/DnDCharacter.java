import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class DnDCharacter {

    private Random random = new Random();

    private final int strength = ability(rollDice());
    private final int dexterity = ability(rollDice());
    private final int constitution = ability(rollDice());
    private final int intelligence = ability(rollDice());
    private final int wisdom = ability(rollDice());
    private final int charism = ability(rollDice());
    private final int hitpoints = 10 + modifier(constitution);

    int ability(List<Integer> scores) {
        return scores
                .stream()
                .sorted((p,q) -> q.compareTo(p))
                .mapToInt(Integer::intValue)
                .limit(3)
                .sum();
    }

    List<Integer> rollDice() {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 4; i++)
            result.add(random.nextInt(5) + 1);
        return result;
    }

    int modifier(int input) {
        return Math.floorDiv(input - 10, 2);
    }

    int getStrength() {
        return this.strength;
    }

    int getDexterity() {
        return this.dexterity;
    }

    int getConstitution() {
        return this.constitution;
    }

    int getIntelligence() {
        return this.intelligence;
    }

    int getWisdom() {
        return this.wisdom;
    }

    int getCharisma() {
        return this.charism;
    }

    int getHitpoints() {
        return this.hitpoints;
    }
}
