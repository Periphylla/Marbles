package berlin.blick.marbles.engine;

import org.junit.Test;

import static berlin.blick.marbles.util.AndroidHelper.arrayCopy;
import static org.assertj.core.api.Assertions.assertThat;

public class MarblesSourceTest {

    @Test
    public void testInitialisation() throws Exception {
        final MarblesSource marblesSource = new MarblesSource(3, 5);
        assertThat(marblesSource.isPending()).isEqualTo(true);
        assertThat(marblesSource.getPending()).isEqualTo(3);
    }

    @Test
    public void testThatNextColorsGetsGenerated() throws Exception {
        final MarblesSource marblesSource = new MarblesSource(3, 5);
        final int[] nextColors = marblesSource.getNextColors();
        for (int color : nextColors) {
            assertThat(color).isGreaterThan(0);
        }
    }

    @Test
    public void testRetrieveNextColorsProcess() throws Exception {
        final MarblesSource source = new MarblesSource(5, 7);
        assertThat(source.isPending()).isEqualTo(true);
        final int[] first = arrayCopy(source.getNextColors(), 0, 5);
        for (int color : first) {
            final int next = source.next();
            assertThat(next).isEqualTo(color);
            assertThat(next).isGreaterThan(0);
        }
        assertThat(source.isPending()).isEqualTo(false);
        source.approve();
        assertThat(source.isPending()).isEqualTo(true);
        final int[] second = arrayCopy(source.getNextColors(), 0, 5);
        for (int color : second) {
            final int next = source.next();
            assertThat(next).isEqualTo(color);
            assertThat(next).isGreaterThan(0);
        }
        checkUnEqualArray(first, second);
    }

    @Test
    public void testReset() throws Exception {
        final MarblesSource marblesSource = new MarblesSource(3, 5);
        marblesSource.reset();
        assertThat(marblesSource.isPending()).isEqualTo(false);
        final int[] nextColors = marblesSource.getNextColors();
        for (int color : nextColors) {
            assertThat(color).isGreaterThan(0);
        }
    }

    private void checkUnEqualArray(int[] first, int[] second) {
        boolean equal = first.length == second.length;
        for (int i = 0; equal && i < first.length; i++) {
            equal = first[i] == second[i];
        }
        assertThat(equal).isEqualTo(false);
    }
}
