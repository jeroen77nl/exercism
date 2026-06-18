class QueenAttackCalculator {

    private final Queen queen1;
    private final Queen queen2;

    QueenAttackCalculator(Queen queen1, Queen queen2) {
        if (queen1 == null || queen2 == null)
            throw new IllegalArgumentException("You must supply valid positions for both Queens.");

        if (queen1.equals(queen2))
            throw new IllegalArgumentException("Queens cannot occupy the same position.");

        this.queen1 = queen1;
        this.queen2 = queen2;
    }

    boolean canQueensAttackOneAnother() {
        if (this.queen1.getRow() == this.queen2.getRow()
                || this.queen1.getColumn() == this.queen2.getColumn()) {
            return true;
        }

        int dRow = Math.abs(this.queen1.getRow() - this.queen2.getRow());
        int dColumn = Math.abs(this.queen1.getColumn() - this.queen2.getColumn());

        return (dRow == dColumn);
    }

}