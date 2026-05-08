import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

enum Status { GOING, LOOP, FINISHED }

class Player {
    String name;
    List<String> deck = new ArrayList<>();
    Player otherPlayer;

    Player(String name) {
        this.name = name;
    }

    void addToDeck(List<String> cards) {
        deck.addAll(cards);
    }

    boolean hasNoCards() {
        return deck.isEmpty();
    }
}

public class Camicia {

    private final List<String> pile = new ArrayList<>();
    private final Set<List<List<String>>> deckHistory = new HashSet<>();

    private final Player playerA;
    private final Player playerB;
    private Player activePlayer;
    private Player paymentCardPlayer;

    private Status status = Status.GOING;
    private int payments = 0;
    private int tricks = 0;
    private int cards = 0;

    static CamiciaResult simulateGame(List<String> deckPlayerA, List<String> deckPlayerB) {
        return new Camicia(deckPlayerA, deckPlayerB).playGame();
    }

    private Camicia(List<String> deckPlayerA, List<String> deckPlayerB) {

        playerA = new Player("A");
        playerA.addToDeck(deckPlayerA);

        playerB = new Player("B");
        playerB.addToDeck(deckPlayerB);

        playerA.otherPlayer = playerB;
        playerB.otherPlayer = playerA;

        activePlayer = playerA;
    }

    private CamiciaResult playGame() {

        writeHistory();

        while (status == Status.GOING) {
            playTurn();
        }

        return new CamiciaResult(status.name().toLowerCase(), cards, tricks);
    }

    private void playTurn() {

        if (activePlayer.hasNoCards()) {
            tricks++;
            status = Status.FINISHED;
            return;
        }

        String card = pushOnPile();

        if (payments == 0 || isPaymentCard(card)) {
            payments = calculatePayments(card);
            if (isPaymentCard(card)) {
                paymentCardPlayer = activePlayer;
            }
            changePlayer();
            return;
        }

        payments--;

        if (payments == 0) {
            tricks++;
            if (paymentCardPlayer.otherPlayer.hasNoCards()) {
                status = Status.FINISHED;
                return;
            }
            activePlayer = paymentCardPlayer;
            activePlayer.addToDeck(pile);
            pile.clear();
            writeHistory();
        }
    }

    private String pushOnPile() {
        String card = activePlayer.deck.removeFirst();
        pile.add(card);
        cards++;
        return card;
    }

    private void writeHistory() {
        List<List<String>> historyEntry = createHistoryEntry();
        if (deckHistory.contains(historyEntry)) {
            status = Status.LOOP;
            return;
        }

        deckHistory.add(historyEntry);
    }

    private List<List<String>> createHistoryEntry() {
        return List.of(
                createDeckHistory(playerA.deck),
                createDeckHistory(playerB.deck));
    }

    private List<String> createDeckHistory(List<String> deck) {
        return deck.stream()
                .map(card -> isPaymentCard(card) ? card : "0")
                .toList();
    }

    private void changePlayer() {
        activePlayer = activePlayer == playerA ? playerB : playerA;
    }

    private int calculatePayments(String card) {
        return switch (card) {
            case "J" -> 1;
            case "Q" -> 2;
            case "K" -> 3;
            case "A" -> 4;
            default -> 0;
        };
    }

    private boolean isPaymentCard(String card) {
        return calculatePayments(card) > 0;
    }
}