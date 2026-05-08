import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Camicia {

    private static final String FINISHED = "finished";
    private static final String LOOP = "loop";

    private final List<String> pile = new ArrayList<>();
    private final List<String> deckPlayerA = new ArrayList<>();
    private final List<String> deckPlayerB = new ArrayList<>();
    private final Set<List<List<String>>> deckHistory = new HashSet<>();

    private String activePlayer = "A";
    private int payments = 0;
    private String status = "";
    private String playerLastPaymentCard = "";
    private int tricks = 0;
    private int cards = 0;

    static CamiciaResult simulateGame(List<String> deckPlayerA, List<String> deckPlayerB) {
        Camicia camicia = new Camicia();
        return camicia.playGame(deckPlayerA, deckPlayerB);
    }

    private CamiciaResult playGame(List<String> deckPlayerA, List<String> deckPlayerB) {
        System.out.printf("%-25s %-25s %-25s  pl   paym   cards  tricks%-50s%n",
                "deck A", "deck B", "pile", "history");
        this.deckPlayerA.addAll(deckPlayerA);
        this.deckPlayerB.addAll(deckPlayerB);
        writeHistory();

        while (status.isEmpty()) {
            playTurn();
        }

        return new CamiciaResult(status, cards, tricks);

    }

    private void playTurn() {
//        System.out.printf("%-25s %-25s %-25s  %-3s  %-5d  %-5d  %-5d%-50s%n",
//                deckPlayerA, deckPlayerB, pile, activePlayer, payments, cards, tricks, deckHistory);
        List<String> deck = activePlayer.equals("A") ? deckPlayerA : deckPlayerB;
        List<String> otherDeck = activePlayer.equals("A") ? deckPlayerB : deckPlayerA;

        if (deck.isEmpty()) {
            otherDeck.addAll(pile);
            pile.clear();
            status = FINISHED;
            tricks++;
        } else if (payments == 0) {
            String card = deck.removeFirst();
            pile.add(card);
            cards++;
            payments = calculatePayments(card);
            if (isPaymentCard(card)) {
                playerLastPaymentCard = activePlayer;
            }
            changePlayer();
        } else {
            String card = deck.removeFirst();
            if (isPaymentCard(card)) {
                pile.add(card);
                cards++;
                payments = calculatePayments(card);
                if (isPaymentCard(card)) {
                    playerLastPaymentCard = activePlayer;
                }
                changePlayer();
            } else {
                pile.add(card);
                cards++;
                payments--;
                if (payments == 0) {
                    List<String> deckThatReceivesPile =
                            playerLastPaymentCard.equals("A") ? deckPlayerA : deckPlayerB;
                    deckThatReceivesPile.addAll(pile);
                    pile.clear();
                    tricks++;
                    if (deckPlayerA.size() + deckPlayerB.size() == deckThatReceivesPile.size()) {
                        status = FINISHED;
                    }
                    activePlayer = playerLastPaymentCard;
                    writeHistory();
                }
            }
        }
    }

    private void writeHistory() {
        List<List<String>> historyPoint = List.of(
                createDeckHistory(deckPlayerA),
                createDeckHistory(deckPlayerB));
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
        activePlayer = "A".equals(activePlayer) ? "B" : "A";
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