import java.util.*;

record Point(int x, int y) {
}

class GoCounting {

    private final Player[][] board;
    private final int xSize;
    private final int ySize;

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
        Territory territory = processInput(x, y);

        if (territory.owners.size() == 1) {
            return territory.owners.stream().toList().getFirst();
        }

        return Player.NONE;
    }

    Set<Point> getTerritory(int x, int y) {
        return processInput(x, y).points();
    }

    Map<Player, Set<Point>> getTerritories() {
        HashMap<Player, Set<Point>> territories = new HashMap<>();
        territories.put(Player.BLACK, new HashSet<>());
        territories.put(Player.WHITE, new HashSet<>());
        territories.put(Player.NONE, new HashSet<>());

        Set<Point> visitedPoints = new HashSet<>();

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                Point point = new Point(x, y);
                if (!visitedPoints.contains(point)) {
                    Player owner = getTerritoryOwner(x, y);
                    Set<Point> territory = getTerritory(x, y);
                    visitedPoints.addAll(territory);
                    Set<Point> oldSet = territories.getOrDefault(owner, new HashSet<>());
                    oldSet.addAll(territory);
                    territories.put(owner, oldSet);
                }
            }
        }

        return territories;
    }

    record Territory(Set<Point> points, Set<Player> owners) {
    }

    private Territory processInput(int x, int y) {
        checkInput(x, y);

        Set<Point> territory = new HashSet<>();
        Set<Player> owners = new HashSet<>();

        if (board[y][x] != Player.NONE)
            return new Territory(territory, owners);

        Deque<Point> queue = new ArrayDeque<>();
        Point start = new Point(x, y);
        queue.addLast(start);
        territory.add(start);

        while (!queue.isEmpty()) {
            Point point = queue.removeFirst();
            List<Point> neighbours = neighboursOf(point);
            for (Point neighbour : neighbours) {
                Player player = board[neighbour.y()][neighbour.x()];
                if (player != Player.NONE) {
                    owners.add(player);
                }
                if (player == Player.NONE && !territory.contains(neighbour)) {
                    territory.add(neighbour);
                    queue.addLast(neighbour);
                }
            }
        }
        return new Territory(territory, owners);
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