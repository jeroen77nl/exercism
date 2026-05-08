import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Player {
    String name;
    List<String> deck = new ArrayList<>();
    Player otherPlayer;

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
    private String status = "GOING";
    private int tricks = 0;
    private int cards = 0;

    static CamiciaResult simulateGame(List<String> deckPlayerA, List<String> deckPlayerB) {
        Camicia camicia = new Camicia();
        return camicia.playGame(deckPlayerA, deckPlayerB);
    }

    private CamiciaResult playGame(List<String> deckPlayerA, List<String> deckPlayerB) {
        playerA.addCards(deckPlayerA);
        playerA.otherPlayer = playerB;

        playerB.addCards(deckPlayerB);
        playerB.otherPlayer = playerA;

        writeHistory();

        while ("GOING".equals(status)) {
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

        String card = pushOnPile();

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
            tricks++;
            if (playerLastPaymentCard.otherPlayer.hasNoCards()) {
                status = FINISHED;
                return;
            }
            activePlayer = playerLastPaymentCard;
            activePlayer.addCards(pile);
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
            status = LOOP;
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