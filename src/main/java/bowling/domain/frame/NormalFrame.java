package bowling.domain.frame;

import bowling.domain.Pin;
import bowling.domain.Round;
import bowling.domain.ScoreSymbol;

public class NormalFrame extends Frame {
    private static final String PIPE = "|";
    public static final int MAX_PLAY_COUNT = 2;

    private ScoreSymbol scoreSymbol;

    private NormalFrame() { }

    public static Frame newInstance() {
        return new NormalFrame();
    }

    @Override
    public void play(int countOfDownPin) {
        if ((getTotalPinCount() + countOfDownPin) > Pin.MAX_PIN_COUNT) {
            throw new IllegalArgumentException("핀을 최대 10개까지 쓰러뜨릴 수 있습니다.");
        }
        pins.add(Pin.from(countOfDownPin));
    }

    @Override
    public Boolean isNextFrame() {
        if (getPinsSize() == MAX_PLAY_COUNT) {
            return true;
        }
        return getTotalPinCount() == Pin.MAX_PIN_COUNT;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public Frame next(Round round) {
        this.scoreSymbol = valueOfScoreSymbol();
        if (round.equals(Round.from(Round.BOWLING_MAX_ROUND))) {
            return FinalFrame.newInstance();
        }
        return NormalFrame.newInstance();
    }

    private ScoreSymbol valueOfScoreSymbol() {
        if (getTotalPinCount() < Pin.MAX_PIN_COUNT) {
            return ScoreSymbol.valueOf(getSecondPin(), isFirst());
        }
        return ScoreSymbol.valueOf(getTotalPinCount(), isFirst());
    }

    public String scoreToString() {
        if (scoreSymbol == null) {
            return String.format("%d", getFirstPin());
        }
        if (scoreSymbol.equals(ScoreSymbol.STRIKE)) {
            return String.format("%1s", ScoreSymbol.STRIKE.getSymbol());
        }
        if (scoreSymbol.equals(ScoreSymbol.SPARE)) {
            return String.format("%d%s%s", getFirstPin(), PIPE, ScoreSymbol.SPARE.getSymbol());
        }
        if (scoreSymbol.equals(ScoreSymbol.GUTTER)) {
            return String.format("%d%s%s", getFirstPin(), PIPE, ScoreSymbol.GUTTER.getSymbol());
        }
        return String.format("%d%s%d", getFirstPin(), PIPE, getSecondPin());
    }
}
