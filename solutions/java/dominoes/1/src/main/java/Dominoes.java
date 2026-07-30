import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Dominoes {

    private Map<Domino, Integer> available;
    private List<Domino> input;
    private List<Domino> state;

    List<Domino> formChain(List<Domino> inputDominoes) throws ChainNotFoundException {
        available = new HashMap<>();
        for (Domino domino : inputDominoes) {
            available.put(domino, available.getOrDefault(domino, 0) + 1);
        }

        input = inputDominoes;
        state = new ArrayList<>();

        if (inputDominoes.isEmpty())
            return state;

        if (!solve()) {
            throw new ChainNotFoundException("No domino chain found.");
        }

        return state;
    }

    private boolean solve() {
        if (isSolution()) {
            return true;
        }

        List<Domino> choices = possibleChoices();
        for (Domino domino : choices) {
            Domino newDomino = domino;
            if (!state.isEmpty() && state.getLast().getRight() != domino.getLeft()) {
                newDomino = new Domino(domino.getRight(), domino.getLeft());
            }

            state.addLast(newDomino);
            available.put(newDomino, available.get(newDomino) - 1);

            if (solve()) {
                return true;
            }

            state.removeLast();
            available.put(newDomino, available.get(newDomino) + 1);
        }

        return false;
    }

    private boolean isSolution() {
        return state.size() == input.size() &&
                state.getFirst().getLeft() == state.getLast().getRight();
    }

    private List<Domino> possibleChoices() {
        if (state.isEmpty()) {
            return new ArrayList<>(input);
        }

        int tailNumber = state.getLast().getRight();
        return available.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .filter(domino ->
                        domino.getLeft() == tailNumber ||
                                domino.getRight() == tailNumber)
                .toList();
    }
}