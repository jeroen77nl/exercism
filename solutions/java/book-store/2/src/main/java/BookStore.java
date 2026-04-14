import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

class BookStore {

    final int[] discounts = {0, 5, 10, 20, 25};

    double calculateBasketCost(List<Integer> books) {
        books = aggregateBooks(books);
        return calculateIter(books, 0, 5).
                orElseThrow(() -> new RuntimeException("totaalprijs kan niet berekend worden"));
    }

    Optional<Double> calculateIter(List<Integer> books, double totalPrice, int maxNumber) {
        if (books.isEmpty())
            return Optional.of(totalPrice);
        if (maxNumber == 0)
            return Optional.empty();

        Optional<Double> result1 = Optional.empty();
        if (maxNumber <= books.size()) {
            List<Integer> booksCopy1 = new ArrayList<>(books);
            for (int i = 0; i < maxNumber; i++) {
                booksCopy1.set(i, booksCopy1.get(i) - 1);
            }
            booksCopy1 = booksCopy1.stream()
                    .filter(n -> n > 0)
                    .collect(Collectors.toCollection(ArrayList::new));
            result1 = calculateIter(
                    booksCopy1,
                    totalPrice + 8.0 * maxNumber * (100 - discounts[maxNumber - 1]) / 100,
                    maxNumber);
        }

        Optional<Double> result2 =
                calculateIter(
                        new ArrayList<>(books),
                        totalPrice,
                        maxNumber - 1);

        Optional<Double> result;
        if (result1.isPresent() && result2.isPresent()) {
            result = Optional.of(Math.min(result1.get(), result2.get()));
        } else if (result1.isPresent()) {
            result = result1;
        } else {
            result = result2;
        }
        return result;
    }

    private List<Integer> aggregateBooks(List<Integer> books) {
        if (books.isEmpty())
            return books;

        List<Integer> b = new ArrayList<>(books);
        b.sort(Integer::compareTo);

        List<Integer> l = new ArrayList<>();
        l.add(1);
        for (int i = 1; i < b.size(); i++) {
            if (Objects.equals(b.get(i - 1), b.get(i))) {
                l.set(l.size() - 1, l.getLast() + 1);
            } else {
                l.add(1);
            }
        }
        l.sort((x,y) -> y.compareTo(x));
        return l;
    }
}