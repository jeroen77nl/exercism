import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GottaSnatchEmAll {

    static Set<String> newCollection(List<String> cards) {
        return new HashSet<>(cards);
    }

    static boolean addCard(String card, Set<String> collection) {
        return collection.add(card);
    }

    static boolean canTrade(Set<String> myCollection, Set<String> theirCollection) {
        return !myCollection.containsAll(theirCollection) && !theirCollection.containsAll(myCollection);
    }

    static Set<String> commonCards(List<Set<String>> collections) {
        if (collections.isEmpty()) {
            return Set.of();
        }

        Set<String> result = new HashSet<>(collections.getFirst());
        collections.stream()
                .skip(1)
                .forEach(set -> result.retainAll(set));
        return result;
    }

    static Set<String> allCards(List<Set<String>> collections) {
        if (collections.isEmpty()) {
            return Set.of();
        }

        Set<String> result = new HashSet<>();
        collections.stream()
                .forEach(set -> result.addAll(set));
        return result;
    }
}
