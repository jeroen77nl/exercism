import java.util.*;

class Yacht {

    private final Map<Integer, Integer> diceMap = new HashMap<>();
    private final YachtCategory yachtCategory;
    private final int sumOfEyes;

    Yacht(int[] dice, YachtCategory yachtCategory) {
        int sum = 0;
        for (int eyes : dice) {
            diceMap.put(eyes, count(eyes) + 1);
            sum += eyes;
        }
        sumOfEyes = sum;
        this.yachtCategory = yachtCategory;
    }

    int score() {

        return switch (yachtCategory) {
            case YACHT -> yacht();
            case ONES, TWOS, THREES, FOURS, FIVES, SIXES -> fixedNumber();
            case FULL_HOUSE -> fullHouse();
            case FOUR_OF_A_KIND -> fourOfAKind();
            case LITTLE_STRAIGHT -> littleStraight();
            case BIG_STRAIGHT -> bigStraight();
            case CHOICE -> sumOfEyes;
        };
    }

    private int count(int eyes) {
        return diceMap.getOrDefault(eyes, 0);
    }

    boolean hasCount(int n) {
        return diceMap.containsValue(n);
    }

    private int fixedNumber() {
        int value = yachtCategory.getFixedValue();
        return count(value) * value;
    }

    private int yacht() {
        return diceMap.size() == 1 ? 50 : 0;
    }

    private int littleStraight() {
        Set<Integer> diceSet = diceMap.keySet();
        return diceSet.equals(Set.of(1, 2, 3, 4, 5)) ? 30 : 0;
    }

    private int bigStraight() {
        Set<Integer> diceSet = diceMap.keySet();
        return diceSet.equals(Set.of(2, 3, 4, 5, 6)) ? 30 : 0;
    }

    private int fourOfAKind() {
        return diceMap.entrySet().stream()
                .filter(e -> e.getValue() >= 4)
                .mapToInt(e -> e.getKey() * 4)
                .findFirst()
                .orElse(0);
    }

    private int fullHouse() {
        return hasCount(3)
                && hasCount(2)
                && diceMap.size() == 2
                ? sumOfEyes
                : 0;
    }
}
