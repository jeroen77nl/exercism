class Darts {
    /*
    If the dart lands outside the target, player earns no points (0 points).
    If the dart lands in the outer circle of the target, player earns 1 point.
    If the dart lands in the middle circle of the target, player earns 5 points.
    If the dart lands in the inner circle of the target, player earns 10 points.
    The outer circle has a radius of 10 units
    (this is equivalent to the total radius for the entire target),
    the middle circle a radius of 5 units, and
    the inner circle a radius of 1.
    Of course, they are all centered at the same point — that is,
    the circles are concentric defined by the coordinates (0, 0).

    Given a point in the target (defined by its Cartesian coordinates x and y,
    where x and y are real), calculate the correct score earned by a dart
    landing at that point.
    */
    enum DartScore {
        INNER_CIRCLE(1,10),
        MIDDLE_CIRCLE(5,5),
        OUTER_CIRCLE(10,1);

        private final int diameter;
        private final int score;

        DartScore(int diameter, int score) {
            this.diameter = diameter;
            this.score = score;
        }

        public int getDiameter() {
            return diameter;
        }

        public int getScore() {
            return score;
        }
    }

    int score(double xOfDart, double yOfDart) {
        double distance = Math.hypot(xOfDart, yOfDart);

        if (distance <= DartScore.INNER_CIRCLE.getDiameter())
            return DartScore.INNER_CIRCLE.getScore();
        else if (distance <= DartScore.MIDDLE_CIRCLE.getDiameter())
            return DartScore.MIDDLE_CIRCLE.getScore();
        else if (distance <= DartScore.OUTER_CIRCLE.getDiameter())
            return DartScore.OUTER_CIRCLE.getScore();
        else
            return 0;
    }
}
