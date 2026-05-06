import java.util.ArrayList;
import java.util.List;

class BowlingGame {

    private final List<Frame> frames = new ArrayList<>();
    private final List<Integer> bonusRolls = new ArrayList<>();

    void roll(int pins) {
        checkCanRoll();
        checkRoll(pins);

        if (frames.isEmpty()) {
            addNewFrame(pins);
        } else {
            Frame frame = frames.getLast();
            if (frame.isIncomplete()) {
                updateLastFrame(frame, pins);
            } else {
                if (frames.size() < 10) {
                    addNewFrame(pins);
                } else {
                    bonusRolls.add(pins);
                }
            }
        }
    }

    private void checkRoll(int pins) {
        if (pins < 0) throw new IllegalStateException("Negative roll is invalid");
        if (pins > 10) throw new IllegalStateException("Pin count exceeds pins on the lane");
        if (bonusRolls.size() == 1 && bonusRolls.getFirst() <= 9 &&
                bonusRolls.getFirst() + pins > 10)
            throw new IllegalStateException("Pin count exceeds pins on the lane");
    }

    private void updateLastFrame(Frame frame, int pins) {
        if (frame.ball1() + pins > 10) throw new IllegalStateException("Pin count exceeds pins on the lane");

        frames.set(frames.size() - 1, new Frame(frame.ball1(), pins));
    }

    private void addNewFrame(int pins) {
        frames.add(new Frame(pins, null));
    }

    int score() {
        checkCanCalculateScore();

        int score = 0;

        for (int i = 0; i < 10; i++) {
            Frame frame = frames.get(i);

            if (frame.isStrike()) {
                score += frame.ball1();
                if (i < 8) {
                    score += frames.get(i + 1).ball1();
                    if (frames.get(i + 1).isStrike()) {
                        score += frames.get(i + 2).ball1();
                    } else {
                        score += frames.get(i + 1).ball2();
                    }
                } else if (i == 8) {
                    score += frames.get(i + 1).ball1();
                    if (frames.get(i + 1).isStrike()) {
                        score += bonusRolls.getFirst();
                    } else {
                        score += frames.get(i + 1).ball2();
                    }
                } else {
                    score += bonusRolls.getFirst() + bonusRolls.getLast();
                }
            } else if (frame.isSpare()) {
                score += frame.ball1() + frame.ball2();
                if (i < 9) {
                    score += frames.get(i + 1).ball1();
                } else {
                    score += bonusRolls.getFirst();
                }
            } else {
                score += frame.ball1() + frame.ball2();
            }
        }
        return score;
    }

    private void checkCanCalculateScore() {

        if (frames.size() < 10)
            throw new IllegalStateException("Score cannot be taken until the end of the game");

        Frame frame = frames.getLast();
        if (frame.isIncomplete()
                || (frame.isSpare() && bonusRolls.size() != 1)
                || (frame.isStrike() && bonusRolls.size() != 2))
            throw new IllegalStateException("Score cannot be taken until the end of the game");
    }

    private void checkCanRoll() {
        if (frames.size() < 10) return;

        Frame lastFrame = frames.getLast();
        if (lastFrame.isOpen()
                || (lastFrame.isSpare() && !bonusRolls.isEmpty())
                || (lastFrame.isStrike() && bonusRolls.size() == 2))
            throw new IllegalStateException("Cannot roll after game is over");
    }

}

//enum FrameType {INCOMPLETE, OPEN, SPARE, STRIKE}

record Frame(int ball1, Integer ball2) {

    private boolean isComplete() {
        return ball1 == 10 || ball2 != null;
    }

    boolean isIncomplete() {
        return !isComplete();
    }

    boolean isStrike() {
        return ball1 == 10;
    }

    boolean isSpare() {
        return ball2 != null && ball1 + ball2 == 10;
    }

    boolean isOpen() {
        return isComplete() && !isStrike() && !isSpare();
    }
}