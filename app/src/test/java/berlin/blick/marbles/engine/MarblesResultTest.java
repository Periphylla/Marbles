package berlin.blick.marbles.engine;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MarblesResultTest {

    @Test
    public void testNextPositionForHorizontal() throws Exception {
        // 0 1 2
        // 3 4 5
        // 6 7 8
        MarblesResult mr = new MarblesResult(0, MarblesResult.HORIZONTAL);
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(1));
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(2));
        assertThat(mr.next(3), is(false));
    }

    @Test
    public void testNextPositionForVertical() throws Exception {
        // 0 1 2
        // 3 4 5
        // 6 7 8
        MarblesResult mr = new MarblesResult(0, MarblesResult.VERTICAL);
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(3));
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(6));
        assertThat(mr.next(3), is(false));

        mr = new MarblesResult(4, MarblesResult.VERTICAL);
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(7));
        assertThat(mr.next(3), is(false));
    }

    @Test
    public void testNextPositionForDiagonalUpwards() throws Exception {
        // 0 1 2
        // 3 4 5
        // 6 7 8
        MarblesResult mr = new MarblesResult(5, MarblesResult.DIAGONAL_UPWARDS);
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(7));
        assertThat(mr.next(3), is(false));

        mr = new MarblesResult(2, MarblesResult.DIAGONAL_UPWARDS);
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(4));
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(6));
        assertThat(mr.next(3), is(false));
    }

    @Test
    public void testNextPositionForDiagonalDownwards() throws Exception {
        // 0 1 2
        // 3 4 5
        // 6 7 8
        MarblesResult mr = new MarblesResult(1, MarblesResult.DIAGONAL_DOWNWARDS);
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(5));
        assertThat(mr.next(3), is(false));

        mr = new MarblesResult(0, MarblesResult.DIAGONAL_DOWNWARDS);
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(4));
        assertThat(mr.next(3), is(true));
        assertThat(mr.getPosition(), is(8));
        assertThat(mr.next(3), is(false));
    }
}