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
            if (frame.frameType() == FrameType.INCOMPLETE) {
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

        FrameType newFrameType = frame.ball1() + pins == 10
                ? FrameType.SPARE
                : FrameType.OPEN;
        frames.set(frames.size() - 1, new Frame(newFrameType, frame.ball1(), pins));
    }

    private void addNewFrame(int pins) {
        FrameType newFrameType = pins < 10
                ? FrameType.INCOMPLETE
                : FrameType.STRIKE;
        frames.add(new Frame(newFrameType, pins, null));
    }

    int score() {
        checkCanCalculateScore();

        int score = 0;

        for (int i = 0; i < 10; i++) {
            Frame frame = frames.get(i);
            FrameType frameType = frame.frameType();

            if (frame.frameType() == FrameType.OPEN) {
                score += frame.ball1() + frame.ball2();
            } else if (frame.frameType() == FrameType.SPARE) {
                score += frame.ball1() + frame.ball2();
                if (i < 9) {
                    score += frames.get(i + 1).ball1();
                } else {
                    score += bonusRolls.getFirst();
                }
            } else { // STRIKE
                score += frame.ball1();
                if (i < 8) {
                    score += frames.get(i + 1).ball1();
                    if (frames.get(i + 1).frameType() == FrameType.STRIKE) {
                        score += frames.get(i + 2).ball1();
                    } else {
                        score += frames.get(i + 1).ball2();
                    }
                } else if (i == 8) {
                    score += frames.get(i + 1).ball1();
                    if (frames.get(i + 1).frameType() == FrameType.STRIKE) {
                        score += bonusRolls.getFirst();
                    } else {
                        score += frames.get(i + 1).ball2();
                    }
                } else {
                    score += bonusRolls.getFirst() + bonusRolls.getLast();
                }
            }
        }
        return score;
    }

    private void checkCanCalculateScore() {

        if (frames.size() < 10)
            throw new IllegalStateException("Score cannot be taken until the end of the game");

        FrameType frameType = frames.getLast().frameType();
        if (frameType == FrameType.INCOMPLETE
                || (frameType == FrameType.SPARE && bonusRolls.size() != 1)
                || (frameType == FrameType.STRIKE && bonusRolls.size() != 2))
            throw new IllegalStateException("Score cannot be taken until the end of the game");
    }

    private void checkCanRoll() {
        if (frames.size() < 10) return;

        Frame lastFrame = frames.getLast();
        if (lastFrame.frameType() == FrameType.OPEN)
            throw new IllegalStateException("Cannot roll after game is over");
        else if (lastFrame.frameType() == FrameType.SPARE && !bonusRolls.isEmpty())
            throw new IllegalStateException("Cannot roll after game is over");
        else if (lastFrame.frameType() == FrameType.STRIKE && bonusRolls.size() == 2)
            throw new IllegalStateException("Cannot roll after game is over");
    }

}

enum FrameType {INCOMPLETE, OPEN, SPARE, STRIKE}

record Frame(FrameType frameType, int ball1, Integer ball2) {
    public String toString() {
        return switch (frameType) {
            case INCOMPLETE -> "(%d, -)".formatted(ball1);
            case OPEN -> "(%d, %d)".formatted(ball1, ball2);
            case SPARE -> "SPARE";
            case STRIKE -> "STRIKE";
        };
    }
}