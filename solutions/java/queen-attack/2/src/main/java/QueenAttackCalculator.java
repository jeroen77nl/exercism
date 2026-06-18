record QueenAttackCalculator(Queen queen1, Queen queen2) {

    QueenAttackCalculator {
        if (queen1 == null || queen2 == null)
            throw new IllegalArgumentException("You must supply valid positions for both Queens.");

        if (queen1.equals(queen2))
            throw new IllegalArgumentException("Queens cannot occupy the same position.");
    }

    boolean canQueensAttackOneAnother() {
        int dRow = Math.abs(queen1.row() - queen2.row());
        int dCol = Math.abs(queen1.column() - queen2.column());

        return dRow == 0
                || dCol == 0
                || dRow == dCol;
    }
}