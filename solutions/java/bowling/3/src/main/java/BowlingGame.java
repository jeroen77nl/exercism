import java.util.ArrayList;
import java.util.List;

class BowlingGame {

    // ChatGpt solution without Frame type, but a list of Integers instead
    private final List<Integer> rolls = new ArrayList<>();

    void roll(int pins) {
        checkCanRoll();
        checkPins(pins);

        // validate frame constraints (simulate current frame)
        validateFramePins(pins);

        rolls.add(pins);
    }

    int score() {
        checkGameComplete();

        int score = 0;
        int i = 0;

        for (int frame = 0; frame < 10; frame++) {
            if (isStrike(i)) {
                score += 10 + rolls.get(i + 1) + rolls.get(i + 2);
                i += 1;
            } else if (isSpare(i)) {
                score += 10 + rolls.get(i + 2);
                i += 2;
            } else {
                score += rolls.get(i) + rolls.get(i + 1);
                i += 2;
            }
        }

        return score;
    }

    // -------------------------
    // Validation
    // -------------------------

    private void checkPins(int pins) {
        if (pins < 0)
            throw new IllegalStateException("Negative roll is invalid");
        if (pins > 10)
            throw new IllegalStateException("Pin count exceeds pins on the lane");
    }

    private void checkCanRoll() {
        if (isGameComplete())
            throw new IllegalStateException("Cannot roll after game is over");
    }

    private void checkGameComplete() {
        if (!isGameComplete())
            throw new IllegalStateException("Score cannot be taken until the end of the game");
    }

    /**
     * Ensures we don't exceed 10 pins within a frame (except strike case).
     */
    private void validateFramePins(int pins) {
        int frame = 0;
        int i = 0;

        // walk existing rolls to find current frame
        while (frame < 9 && i < rolls.size()) {
            if (rolls.get(i) == 10) {
                i += 1;
            } else {
                i += 2;
            }
            frame++;
        }

        // now we're in 10th frame or earlier incomplete frame
        if (frame < 9) {
            if (isSecondRollInFrame()) {
                int first = rolls.getLast();
                if (first + pins > 10) {
                    throw new IllegalStateException("Pin count exceeds pins on the lane");
                }
            }
        } else {
            // 10th frame logic
            int rollsInTenth = rolls.size() - i;

            if (rollsInTenth == 1) {
                int first = rolls.get(i);
                if (first != 10 && first + pins > 10) {
                    throw new IllegalStateException("Pin count exceeds pins on the lane");
                }
            }

            if (rollsInTenth == 2) {
                int first = rolls.get(i);
                int second = rolls.get(i + 1);

                // if first wasn't strike, no third roll allowed unless spare
                if (first != 10 && first + second < 10) {
                    throw new IllegalStateException("Cannot roll after game is over");
                }

                // if strike then second+third cannot exceed 10 unless second was strike
                if (first == 10 && second != 10 && second + pins > 10) {
                    throw new IllegalStateException("Pin count exceeds pins on the lane");
                }
            }
        }
    }

    /**
     * Determines if the game is complete by simulating frames.
     */
    private boolean isGameComplete() {
        int frame = 0;
        int i = 0;

        while (frame < 9) {
            if (i >= rolls.size()) return false;

            if (rolls.get(i) == 10) {
                i += 1;
            } else {
                if (i + 1 >= rolls.size()) return false;
                i += 2;
            }
            frame++;
        }

        // 10th frame
        if (i >= rolls.size()) return false;

        int first = rolls.get(i);

        if (first == 10) {
            if (i + 2 >= rolls.size()) return false;
            return true;
        }

        if (i + 1 >= rolls.size()) return false;

        int second = rolls.get(i + 1);

        if (first + second == 10) {
            return i + 2 < rolls.size();
        }

        return true;
    }

    // -------------------------
    // Scoring helpers
    // -------------------------

    private boolean isStrike(int i) {
        return rolls.get(i) == 10;
    }

    private boolean isSpare(int i) {
        return rolls.get(i) + rolls.get(i + 1) == 10;
    }

    private boolean isSecondRollInFrame() {
        int i = 0;

        while (i < rolls.size()) {
            if (rolls.get(i) == 10) {
                i += 1; // strike frame
            } else {
                if (i == rolls.size() - 1) {
                    return true; // we're at second roll
                }
                i += 2;
            }
        }
        return false;
    }
}