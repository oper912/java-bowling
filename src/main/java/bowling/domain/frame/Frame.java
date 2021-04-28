package bowling.domain.frame;

import bowling.domain.Round;
import bowling.domain.Pin;

import java.util.ArrayList;
import java.util.List;

public abstract class Frame {
    protected List<Pin> pins;

    public Frame() {
        pins = new ArrayList<>();
    }

    abstract public Frame next(Round round);

    abstract public void play(int countOfDownPin);

    public abstract boolean isEnd();

    public abstract Boolean isNextFrame();

    public int getPinsSize() {
        return pins.size();
    }

    public int getTotalPinCount() {
        return pins.stream()
                .mapToInt(Pin::getPin)
                .sum();
    }

    public int getFirstPin() {
        return pins.get(PinSequence.FIRST.getSequence()).getPin();
    }

    public int getSecondPin() {
        return pins.get(PinSequence.SECOND.getSequence()).getPin();
    }

    public int getBonusPin() {
        return pins.get(PinSequence.BONUS.getSequence()).getPin();
    }

    public boolean isFirst() {
        return pins.size() == PlaySequence.FIRST.getSequence();
    }

    public boolean isSecond() {
        return pins.size() == PlaySequence.SECOND.getSequence();
    }

    public boolean isBonus() {
        return pins.size() == PlaySequence.BONUS.getSequence();
    }
}
