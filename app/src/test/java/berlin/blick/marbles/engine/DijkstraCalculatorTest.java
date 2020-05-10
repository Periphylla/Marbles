package berlin.blick.marbles.engine;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DijkstraCalculatorTest {

    @Test
    public void calculateSmallDistance() {
        // arrange ...
        int[] stage = new int[] {
                0, 0, 0,    // 0 1 2
                0, 0, 0,    // 3 4 5
                0, 0, 0     // 6 7 8
        };
        DijkstraCalculator calculator = new DijkstraCalculator(9);
        // act ...
        final int[] way = calculator.retrievePath(0, 5, new MarblesFieldAgent(stage, 3));
        // assert...
        assertThat(way.length, is(4));
        assertThat(way[0], is(0));
        assertThat(way[1], is(1));
        assertThat(way[2], is(2));
        assertThat(way[3], is(5));
    }

    @Test
    public void testNoResults() {
        // arrange ...
        int[] stage = new int[] {
                0, 1, 0,
                1, 1, 0,
                0, 0, 0
        };
        DijkstraCalculator calculator = new DijkstraCalculator(9);
        // act ...
        final int[] way = calculator.retrievePath(0, 5, new MarblesFieldAgent(stage, 3));
        // assert...
        assertThat(way.length, is(0));
    }

    @Test
    public void calculateFromMiddleOfField() {
        // arrange ...
        int[] stage = new int[] {
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 11, 0, 0, 0,
                0, 0, 0, 18, 0,
                0, 0, 0, 0, 0
        };
        DijkstraCalculator calculator = new DijkstraCalculator(stage.length);
        // act ...
        final int[] way = calculator.retrievePath(11, 23, new MarblesFieldAgent(stage, 5));
        // assert...
        assertThat(way.length, is(5));
        assertThat(way[0], is(11));
        assertThat(way[1], is(12));
        assertThat(way[2], is(17));
        assertThat(way[3], is(22));
        assertThat(way[4], is(23));
    }

    @Test
    public void testUseShorterWay() {
        // arrange ...
        int[] stage = new int[] {
                0, 5, 0, 0, 0,
                0, 1, 0, 1, 0,
                0, 0, 0, 0, 0,
                1, 1, 1, 0, 1,
                0, 0, 0, 0, 0
        };
        DijkstraCalculator calculator = new DijkstraCalculator(stage.length);
        // act ...
        final int[] way = calculator.retrievePath(1, 18, new MarblesFieldAgent(stage, 5));
        // assert...
        assertThat(way.length, is(6));
        assertThat(way[0], is(1));
        assertThat(way[1], is(2));
        assertThat(way[2], is(7));
        assertThat(way[3], is(12));
        assertThat(way[4], is(13));
        assertThat(way[5], is(18));
    }

    @Test
    public void calculateAroundBarrier() {
        // arrange ...
        int[] stage = new int[] {
                0, 5, 0, 0, 0, 0, 0,
                0, 1, 0, 1, 1, 1, 0,
                0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 0, 1, 1, 1,
                0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 1, 1, 1, 1,
                0, 0, 0, 0, 0, 0, 0
        };
        DijkstraCalculator calculator = new DijkstraCalculator(stage.length);
        // act ...
        final int[] way = calculator.retrievePath(1, 28, new MarblesFieldAgent(stage, 7));
        // assert...
        assertThat(way.length, is(10));
        assertThat(way[0], is(1));
        assertThat(way[1], is(2));
        assertThat(way[2], is(9));
        assertThat(way[3], is(16));
        assertThat(way[4], is(17));
        assertThat(way[5], is(24));
        assertThat(way[6], is(31));
        assertThat(way[7], is(30));
        assertThat(way[8], is(29));
        assertThat(way[9], is(28));
    }
}
