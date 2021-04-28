package bowling.view;

import bowling.domain.Frames;
import bowling.domain.Round;
import bowling.domain.ScoreSymbol;
import bowling.domain.frame.Frame;
import bowling.domain.frame.NormalFrame;
import bowling.domain.Pin;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResultView {
    private static final String DEFAULT_SCORE_BOARD = "| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |";
    private static final String EMPTY_FRAME = "      |";
    private static final String PIPE = "|";

    public void printScoreBoard(String player, Frames frames) {
        System.out.println(DEFAULT_SCORE_BOARD);
        StringBuilder builder = new StringBuilder();
        builder.append(playerToString(player));
        builder.append(printProgressFrames(frames));
        builder.append(printEmptyFrame(frames));
        System.out.println(builder.toString());
    }

    private String playerToString(String player) {
        return String.format(PIPE + "  %3s " + PIPE, player);
    }

    private String printProgressFrames(Frames frames) {
        return IntStream.range(0, frames.getProgressFrames().size())
                .mapToObj(index -> frames.getFrames().get(index))
                .map(frame -> frameToString(frame))
                .collect(Collectors.joining());
    }

    private String frameToString(Frame frame) {
        if (frame instanceof NormalFrame) {
            return normalFrameScoreToString((NormalFrame) frame);
        }
        return String.format("%5s |", finalFrameScoreToString(frame));
    }

    private String normalFrameScoreToString(NormalFrame frame) {
        return String.format(" %3s  |", frame.scoreToString());
    }

    private String finalFrameScoreToString(Frame frame) {
        int firstPin = frame.getFirstPin();
        if (frame.isFirst()) {
            return pinToString(firstPin);
        }
        if (frame.isSecond()) {
            return pinToString(firstPin) + PIPE +  nextPinToString(frame);
        }
        if (frame.isBonus()) {
            int bonusPin = frame.getBonusPin();
            return pinToString(firstPin) + PIPE + nextPinToString(frame) + PIPE + pinToString(bonusPin);
        }
        return pinToString(firstPin) + PIPE + nextPinToString(frame);
    }

    private String pinToString(int pin) {
        if (pin == Pin.MAX_PIN_COUNT) {
            return ScoreSymbol.STRIKE.getSymbol();
        }
        if (pin == Pin.MIN_PIN_COUNT) {
            return ScoreSymbol.GUTTER.getSymbol();
        }
        return String.valueOf(pin);
    }

    private String nextPinToString(Frame frame) {
        int firstPin = frame.getFirstPin();
        int secondPin = frame.getSecondPin();

        if ((firstPin + secondPin) == Pin.MAX_PIN_COUNT) {
            return ScoreSymbol.SPARE.getSymbol();
        }
        return pinToString(secondPin);
    }

    private String printEmptyFrame(Frames frames) {
        return IntStream.range(frames.getProgressFrames().size(), Round.BOWLING_MAX_ROUND)
                .mapToObj(index -> EMPTY_FRAME)
                .collect(Collectors.joining());
    }
}
