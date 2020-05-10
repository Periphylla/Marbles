package berlin.blick.marbles.util;

import org.junit.Test;

import static berlin.blick.marbles.util.AndroidHelper.arrayCopy;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AndroidHelperTest {

    @Test
    public void test_arrayCopy_returns_correct_size() throws Exception {
        int[] array = new int[7];
        final int[] result = arrayCopy(array, 2, 3);
        assertThat(result, notNullValue());
        assertThat(result.length, is(3));
    }

    @Test
    public void test_that_arrayCopy_returns_correct_values() throws Exception {
        int[] array = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        final int[] result = arrayCopy(array, 2, 3);
        assertThat(result, notNullValue());
        assertThat(result[0], is(3));
        assertThat(result[1], is(4));
        assertThat(result[2], is(5));
    }
}
