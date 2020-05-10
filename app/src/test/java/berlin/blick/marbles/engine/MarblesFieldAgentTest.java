package berlin.blick.marbles.engine;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MarblesFieldAgentTest {

    @Test
    public void testGetNeighboursTop() throws Exception {
        // arrange ...
        int[] stage = new int[] {
                1, 0, 0,
                0, 0, 0,
                0, 0, 1
        };
        MarblesFieldAgent agent = new MarblesFieldAgent(stage, 3);
        // act ...
        final int[] neighbours = agent.getNeighbours(1);
        // assert ...
        assertThat(neighbours.length, is(2));
        assertThat(neighbours[0], is(2));
        assertThat(neighbours[1], is(4));
    }

    @Test
    public void testGetNeighboursMiddle() throws Exception {
        // arrange ...
        int[] stage = new int[] {
                1, 0, 0,
                0, 0, 0,
                0, 0, 1
        };
        MarblesFieldAgent agent = new MarblesFieldAgent(stage, 3);
        // act ...
        final int[] neighbours = agent.getNeighbours(4);
        // assert ...
        assertThat(neighbours.length, is(4));
        assertThat(neighbours[0], is(1));
        assertThat(neighbours[1], is(3));
        assertThat(neighbours[2], is(5));
        assertThat(neighbours[3], is(7));
    }

    @Test
    public void testGetNeighboursTopLeft() throws Exception {
        // arrange ...
        int[] stage = new int[] {
                1, 0, 0,
                0, 0, 0,
                0, 0, 1
        };
        MarblesFieldAgent agent = new MarblesFieldAgent(stage, 3);
        // act ...
        final int[] neighbours = agent.getNeighbours(0);
        // assert ...
        assertThat(neighbours.length, is(2));
        assertThat(neighbours[0], is(1));
        assertThat(neighbours[1], is(3));
    }

    @Test
    public void testGetNeighboursWithTakenField() throws Exception {
        // arrange ...
        int[] stage = new int[] {
                1, 0, 0,
                0, 0, 0,
                0, 0, 1
        };
        MarblesFieldAgent agent = new MarblesFieldAgent(stage, 3);
        // act ...
        final int[] neighbours = agent.getNeighbours(5);
        // assert ...
        assertThat(neighbours.length, is(2));
        assertThat(neighbours[0], is(2));
        assertThat(neighbours[1], is(4));
    }

    @Test
    public void testGetNeighboursBottom() throws Exception {
        // arrange ...
        int[] stage = new int[] {
                1, 0, 0,
                0, 0, 0,
                0, 0, 0
        };
        MarblesFieldAgent agent = new MarblesFieldAgent(stage, 3);
        // act ...
        final int[] neighbours = agent.getNeighbours(7);
        // assert ...
        assertThat(neighbours.length, is(3));
        assertThat(neighbours[0], is(4));
        assertThat(neighbours[1], is(6));
        assertThat(neighbours[2], is(8));
    }

    @Test
    public void testGetNeighboursWithEmptyResult() throws Exception {
        // arrange ...
        int[] stage = new int[] {
                1, 0, 0,
                0, 1, 0,
                1, 0, 0
        };
        MarblesFieldAgent agent = new MarblesFieldAgent(stage, 3);
        // act ...
        final int[] neighbours = agent.getNeighbours(3);
        // assert ...
        assertThat(neighbours.length, is(0));
    }
}
