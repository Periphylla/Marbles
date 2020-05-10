package berlin.blick.marbles.engine;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarblesHistoryUnitTest {
    @Test
    public void testStoreAndRestore() throws Exception {
        // arrange ...
        final int [] stage = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        final int[] einser = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        final int[] zweier = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        final MarblesHistory history = new MarblesHistory(stage);
        // act ...
        history.push(stage);
        history.push(einser);
        history.push(zweier);
        // assert ...
        assertThat(history.pop()).isEqualTo(zweier);
        assertThat(history.pop()).isEqualTo(einser);
        assertThat(history.pop()).isEqualTo(stage);
        assertThat(history.pop()).isEqualTo(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    }

    @Test
    public void testThatOnPushStepStaysBeyond10() throws Exception {
        final MarblesHistory history = new MarblesHistory(new int[4]);
        for (int i = 0; i < 1000; i++) {
            history.push(new int[4]);
            assertThat(history.getStep()).isLessThan(10);
            assertThat(history.getStep()).isGreaterThanOrEqualTo(0);
        }
    }

    @Test
    public void testThatOnPopStepStaysEqualOrAbove0() throws Exception {
        final MarblesHistory history = new MarblesHistory(new int[4]);
        for (int i = 0; i < 1000; i++) {
            history.pop();
            assertThat(history.getStep()).isLessThan(10);
            assertThat(history.getStep()).isGreaterThanOrEqualTo(0);
        }
    }
}
