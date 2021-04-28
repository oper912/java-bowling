package bowling.domain.frame;

import bowling.domain.Round;
import bowling.domain.ScoreSymbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NormalFrameTest {

    @DisplayName("핀을 10개 초과로 쓰러뜨리면 예외가 발생한다")
    @Test
    void valid_max_pin() {
        Frame frame = NormalFrame.newInstance();
        assertThatThrownBy(() -> {
            frame.play(11);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("핀을 최대 10개까지 쓰러뜨릴 수 있습니다.");
    }

    @DisplayName("라운드 진행중일떄 첫번째 투구가 스트라이크가 아니면 출력시 쓰러뜨린 핀만 출력한다")
    @Test
    void scoreToString() {
        // given
        Frame frame = NormalFrame.newInstance();
        int countOfDownPin = 9;

        // when
        frame.play(countOfDownPin);

        // then
        assertThat(((NormalFrame) frame).scoreToString()).isEqualTo(String.valueOf(countOfDownPin));
    }

    @DisplayName("첫번째 투구가 스트라이크이면 출력시 스트라이크 심볼만 출력한다")
    @Test
    void scoreToString_strike() {
        // given
        Frame frame = NormalFrame.newInstance();
        int countOfDownPin = 10;

        // when
        frame.play(countOfDownPin);
        frame.next(Round.from(2));

        // then
        assertThat(((NormalFrame) frame).scoreToString()).isEqualTo(ScoreSymbol.STRIKE.getSymbol());
    }

    @DisplayName("스페어처리를 하면 출력시 \"첫번째투구|게터심볼\" 형태로 출력한다")
    @Test
    void scoreToString_spare() {
        // given
        Frame frame = NormalFrame.newInstance();
        int firstCountOfDownPin = 5;
        int secondCountOfDownPin = 5;

        // when
        frame.play(firstCountOfDownPin);
        frame.play(secondCountOfDownPin);
        frame.next(Round.from(2));

        // then
        assertThat(((NormalFrame) frame).scoreToString())
                .isEqualTo(String.format("%d|%s", firstCountOfDownPin, ScoreSymbol.SPARE.getSymbol()));
    }

    @DisplayName("게터일 때 출력을 하면 \"첫번째투구|게터심볼\" 형태로 출력한다")
    @Test
    void scoreToString_gutter() {
        // given
        Frame frame = NormalFrame.newInstance();
        int firstCountOfDownPin = 5;
        int secondCountOfDownPin = 0;

        // when
        frame.play(firstCountOfDownPin);
        frame.play(secondCountOfDownPin);
        frame.next(Round.from(2));

        assertThat(((NormalFrame) frame).scoreToString())
                .isEqualTo(String.format("%d|%s", firstCountOfDownPin, ScoreSymbol.GUTTER.getSymbol()));
    }

    @DisplayName("미스일 때 출력을 하면 \"첫번째투구|두번째투구\" 형태로 출력한다")
    @Test
    void scoreToString_miss() {
        // given
        Frame frame = NormalFrame.newInstance();
        int firstCountOfDownPin = 5;
        int secondCountOfDownPin = 3;

        // when
        frame.play(firstCountOfDownPin);
        frame.play(secondCountOfDownPin);
        frame.next(Round.from(2));

        // then
        assertThat(((NormalFrame) frame).scoreToString())
                .isEqualTo(String.format("%d|%d", firstCountOfDownPin, secondCountOfDownPin));
    }
}
