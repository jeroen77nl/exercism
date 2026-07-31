import java.util.*;

record Point(int x, int y) {
};

class GoCounting {

    private final Player[][] board;
    private final int xSize;
    private final int ySize;
    private Set<Player> owners;
    private Set<Point> visited;

    GoCounting(String boardString) {
        if (boardString.isEmpty()) throw new RuntimeException("Board string is empty");

        String[] boardStrings = boardString.split("\n");
        ySize = boardStrings.length;
        xSize = boardStrings[0].length();

        board = new Player[ySize][xSize];

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (boardStrings[y].charAt(x) == 'B') {
                    board[y][x] = Player.BLACK;
                } else if (boardStrings[y].charAt(x) == 'W') {
                    board[y][x] = Player.WHITE;
                } else {
                    board[y][x] = Player.NONE;
                }
            }
        }
    }

    Player getTerritoryOwner(int x, int y) {
        processInput(x, y);

        if (owners.size() == 1) {
            return owners.stream().toList().getFirst();
        }

        return Player.NONE;
    }

    Set<Point> getTerritory(int x, int y) {
        processInput(x, y);
        return visited;
    }

    Map<Player, Set<Point>> getTerritories() {
        HashMap<Player, Set<Point>> territories = new HashMap<>();
        territories.put(Player.BLACK,new HashSet<>());
        territories.put(Player.WHITE, new HashSet<>());
        territories.put(Player.NONE, new HashSet<>());

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                Player owner = getTerritoryOwner(x, y);
                Set<Point> territory = getTerritory(x, y);
                Set<Point> oldSet = territories.getOrDefault(owner, new HashSet<>());
                oldSet.addAll(territory);
                territories.put(owner, oldSet);
            }
        }

        return territories;
    }

    private void processInput(int x, int y) {
        checkInput(x, y);
        visited = new HashSet<>();
        owners = new HashSet<>();

        if (board[y][x] != Player.NONE)
            return;

        Deque<Point> queue = new ArrayDeque<>();
        Point start = new Point(x, y);
        queue.addLast(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point point = queue.removeFirst();
            List<Point> neighbours = neighboursOf(point);
            for (Point neighbour : neighbours) {
                if (board[neighbour.y()][neighbour.x()].equals(Player.BLACK)
                        || board[neighbour.y()][neighbour.x()].equals(Player.WHITE)) {
                    owners.add(board[neighbour.y()][neighbour.x()]);
                }
                if (board[neighbour.y()][neighbour.x()].equals(Player.NONE)
                        && !visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.addLast(neighbour);
                }
            }
        }
    }

    private List<Point> neighboursOf(Point point) {
        List<Point> result = new ArrayList<>();
        List<Point> targets = List.of(
                new Point(point.x() - 1, point.y()),
                new Point(point.x() + 1, point.y()),
                new Point(point.x(), point.y() - 1),
                new Point(point.x(), point.y() + 1)
        );
        for (Point target : targets) {
            if (target.x() >= 0 && target.x() < xSize
                    && target.y() >= 0 && target.y() < ySize) {
                result.add(target);
            }
        }
        return result;
    }

    private boolean validPoint(Point p) {
        return p.x() >= 0 && p.x() < xSize
                && p.y() >= 0 && p.y() < ySize;
    }

    private void checkInput(int x, int y) {
        if (!validPoint(new Point(x, y)))
            throw new IllegalArgumentException("Invalid coordinate");
    }

}