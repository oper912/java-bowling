package bowling.domain.frame;

public enum PinSequence {
    FIRST(0), SECOND(1), BONUS(2);

    private int sequence;

    PinSequence(final int sequence) {
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }
}
