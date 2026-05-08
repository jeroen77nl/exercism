import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Player {
    String name;
    List<String> deck = new ArrayList<>();

    Player(String name) {
        this.name = name;
    }

    void addCards(List<String> cards) {
        deck.addAll(cards);
    }

    boolean hasNoCards() {
        return deck.isEmpty();
    }
}

public class Camicia {

    private static final String FINISHED = "finished";
    private static final String LOOP = "loop";

    private final List<String> pile = new ArrayList<>();
    private final Player playerA = new Player("A");
    private final Player playerB = new Player("B");
    private final Set<List<List<String>>> deckHistory = new HashSet<>();

    private Player activePlayer = playerA;
    private Player playerLastPaymentCard;

    private int payments = 0;
    private String status = "";
    private int tricks = 0;
    private int cards = 0;

    static CamiciaResult simulateGame(List<String> deckPlayerA, List<String> deckPlayerB) {
        Camicia camicia = new Camicia();
        return camicia.playGame(deckPlayerA, deckPlayerB);
    }

    private CamiciaResult playGame(List<String> deckPlayerA, List<String> deckPlayerB) {
        playerA.addCards(deckPlayerA);
        playerB.addCards(deckPlayerB);

        writeHistory();

        while (status.isEmpty()) {
            playTurn();
        }

        return new CamiciaResult(status, cards, tricks);
    }

    private void playTurn() {

        if (activePlayer.hasNoCards()) {
            status = FINISHED;
            tricks++;
            return;
        }

        String card = activePlayer.deck.removeFirst();
        pile.add(card);
        cards++;

        if (payments == 0 || isPaymentCard(card)) {
            payments = calculatePayments(card);
            if (isPaymentCard(card)) {
                playerLastPaymentCard = activePlayer;
            }
            changePlayer();
            return;
        }

        payments--;

        if (payments == 0) {
            playerLastPaymentCard.addCards(pile);
            pile.clear();
            tricks++;
            if (playerA.deck.isEmpty() || playerB.deck.isEmpty()) {
                status = FINISHED;
            } else {
                activePlayer = playerLastPaymentCard;
                writeHistory();
            }
        }
    }

    private void writeHistory() {
        List<List<String>> historyPoint = List.of(
                createDeckHistory(playerA.deck),
                createDeckHistory(playerB.deck));
        if (deckHistory.contains(historyPoint)) {
            status = LOOP;
            return;
        }

        deckHistory.add(historyPoint);
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