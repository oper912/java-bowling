package bowling.domain.frame;

public enum PlaySequence {
    FIRST(1), SECOND(2), BONUS(3);

    private int sequence;

    PlaySequence(final int sequence) {
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }
}
