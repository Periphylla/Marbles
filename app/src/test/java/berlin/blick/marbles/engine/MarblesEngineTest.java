package berlin.blick.marbles.engine;

import org.junit.Test;

import static berlin.blick.marbles.engine.MarblesResult.DIAGONAL_DOWNWARDS;
import static berlin.blick.marbles.engine.MarblesResult.DIAGONAL_UPWARDS;
import static berlin.blick.marbles.engine.MarblesResult.HORIZONTAL;
import static berlin.blick.marbles.engine.MarblesResult.VERTICAL;
import static org.assertj.core.api.Assertions.assertThat;

public class MarblesEngineTest {

    @Test
    public void testThatHorizontalPatternIsFound() {
        // arrange ...
        int[] stage = new int[] {
                0, 0, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                2, 2, 2, 2, 2, 0, 0, 0, 0,
                0, 0, 0, 0, 3, 3, 3, 3, 3,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 2, 0, 1, 1, 0, 0, 0,  // test anti pattern
                0, 3, 5, 1, 5, 1, 0, 0, 0,  // test anti pattern
                0, 0, 0, 0, 0, 0, 4, 4, 4,
                4, 4, 0, 0, 0, 0, 0, 0, 0
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 9, 0);
        final MarblesResult result = marblesEngine.determineFullPattern();
        // assert ...
        assertThat(marblesEngine.isHorizontal(18)).isEqualTo(true);
        assertThat(marblesEngine.isHorizontal(31)).isEqualTo(true);
        assertThat(marblesEngine.isHorizontal(9)).isEqualTo(false);
        assertThat(marblesEngine.isHorizontal(49)).isEqualTo(false);
        assertThat(marblesEngine.isHorizontal(57)).isEqualTo(false);
        assertThat(marblesEngine.isHorizontal(78)).isEqualTo(false);
        assertThat(result.isSuccess()).isEqualTo(true);
        assertThat(result.getPosition()).isEqualTo(2);
        assertThat(result.getDirection()).isEqualTo(HORIZONTAL);
    }

    @Test
    public void testThatVerticalPatternIsFound() {
        // arrange ...
        int[] stage = new int[] {
                0, 0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 1, 0, 0, 1, 0, 1, 0,
                0, 0, 1, 0, 2, 2, 0, 1, 0,
                0, 0, 1, 0, 2, 3, 0, 0, 0,
                0, 0, 1, 0, 2, 2, 0, 0, 0,
                0, 0, 1, 0, 2, 1, 0, 0, 0,
                0, 0, 0, 0, 2, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 1, 0, 0
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 9, 0);
        final MarblesResult result = marblesEngine.determineFullPattern();
        // assert ...
        assertThat(marblesEngine.isVertical(0)).isEqualTo(false);
        assertThat(marblesEngine.isVertical(11)).isEqualTo(true);
        assertThat(marblesEngine.isVertical(22)).isEqualTo(true);
        assertThat(marblesEngine.isVertical(49)).isEqualTo(false);
        assertThat(marblesEngine.isVertical(55)).isEqualTo(false);
        assertThat(result.isSuccess()).isEqualTo(true);
        assertThat(result.getPosition()).isEqualTo(11);
        assertThat(result.getDirection()).isEqualTo(VERTICAL);
    }

    @Test
    public void testThatDiagonalUpwardsPatternIsFound() {
        // arrange ...
        int[] stage = new int[] {
                0, 0, 0, 2, 6, 0, 0, 0, 0,
                3, 0, 2, 6, 0, 0, 0, 0, 0,
                0, 2, 6, 0, 1, 0, 0, 4, 3,
                2, 6, 0, 1, 0, 0, 4, 3, 0,
                6, 0, 1, 0, 0, 4, 3, 0, 5,
                0, 1, 0, 0, 4, 3, 0, 5, 0,
                1, 0, 0, 4, 0, 0, 5, 0, 0,
                0, 0, 0, 0, 0, 5, 0, 0, 0,
                0, 0, 0, 0, 5, 0, 0, 0, 0
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 9, 0);
        final MarblesResult result = marblesEngine.determineFullPattern();
        // assert ...
        assertThat(marblesEngine.isDiagonalUpwards(3)).isEqualTo(false);
        assertThat(marblesEngine.isDiagonalUpwards(4)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalUpwards(9)).isEqualTo(false);
        assertThat(marblesEngine.isDiagonalUpwards(22)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalUpwards(25)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalUpwards(26)).isEqualTo(false);
        assertThat(marblesEngine.isDiagonalUpwards(44)).isEqualTo(true);
        assertThat(result.isSuccess()).isEqualTo(true);
        assertThat(result.getPosition()).isEqualTo(4);
        assertThat(result.getDirection()).isEqualTo(DIAGONAL_UPWARDS);
    }

    @Test
    public void testThatDiagonalDownwardsPatternIsFound() {
        // arrange ...
        int[] stage = new int[] {
                1, 0, 0, 0, 0, 2, 0, 0, 0,
                0, 1, 5, 0, 0, 0, 2, 0, 0,
                0, 0, 1, 5, 0, 0, 0, 2, 0,
                0, 0, 0, 1, 5, 0, 0, 0, 2,
                2, 0, 0, 0, 1, 5, 0, 0, 0,
                2, 0, 0, 0, 0, 1, 5, 0, 0,
                0, 0, 0, 0, 0, 0, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 1
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 9, 0);
        final MarblesResult result = marblesEngine.determineFullPattern();
        // assert ...
        assertThat(marblesEngine.isDiagonalDownwards(0)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalDownwards(11)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalDownwards(5)).isEqualTo(false);
        assertThat(marblesEngine.isDiagonalDownwards(10)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalDownwards(20)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalDownwards(30)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalDownwards(40)).isEqualTo(true);
        assertThat(marblesEngine.isDiagonalDownwards(50)).isEqualTo(false);
        assertThat(result.isSuccess()).isEqualTo(true);
        assertThat(result.getPosition()).isEqualTo(0);
        assertThat(result.getDirection()).isEqualTo(DIAGONAL_DOWNWARDS);
    }

    @Test
    public void testAddMarble() throws Exception {
        int[] stage = new int[] {
                1, 1, 1,
                1, 0, 2,
                2, 3, 3
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 3, 0);
        marblesEngine.addMarbleIfNeeded();
        // assert ...
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(0);
        assertThat(marblesEngine.getStage()[5]).isNotEqualTo(0);
        assertThat(marblesEngine.isLost()).isEqualTo(false);
    }

    @Test
    public void testIsLost() throws Exception {
        int[] stage = new int[] {
                1, 1, 1,
                1, 5, 2,
                2, 3, 3
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 3, 0);
        marblesEngine.addMarbleIfNeeded();
        // assert ...
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.isLost()).isEqualTo(true);
    }

    @Test
    public void testHandleClickOn() throws Exception {
        int[] stage = new int[] {
                1, 1, 1, 1, 0,  //  0, 1, 2, 3, 4
                0, 0, 0, 0, 0,  //  5, 6, 7, 8, 9
                1, 1, 1, 2, 2,  // 10,11,12,13,14
                1, 1, 1, 2, 2,  // 15,16,17,18,18
                1, 1, 1, 2, 2   // 20,21,22,23,24
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 5, 0) {
            @Override boolean isTimeForAnimation() { return true; }
        };
        completeHandleClickOn(marblesEngine, 14);
        // assert ...
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.isLost()).isEqualTo(false);
        assertThat(marblesEngine.getSelected()).isEqualTo(14);
        assertThat(marblesEngine.getStage()[marblesEngine.getSelected()]).isEqualTo(2);

        // go on...
        // act...
        completeHandleClickOn(marblesEngine, 10);
        // assert ...
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.isLost()).isEqualTo(false);
        assertThat(marblesEngine.getSelected()).isEqualTo(10);
        assertThat(marblesEngine.getStage()[marblesEngine.getSelected()]).isEqualTo(1);

        // go on...
        // act...
        completeHandleClickOn(marblesEngine, 5);
        // assert ...
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.isLost()).isEqualTo(false);
        assertThat(marblesEngine.getSelected()).isEqualTo(-1);
        assertThat(marblesEngine.getStage()[0]).isEqualTo(1);
        assertThat(marblesEngine.getStage()[5]).isEqualTo(1);
        assertThat(marblesEngine.getStage()[10]).isEqualTo(0);
    }

    @Test
    public void testHandleClickOnWithSuccess() throws Exception {
        int[] stage = new int[] {
                1, 1, 1, 1, 0,
                0, 0, 0, 0, 0,
                1, 1, 1, 2, 2,
                1, 1, 1, 2, 2,
                1, 1, 1, 2, 2
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 5, 0) {
            @Override boolean isTimeForAnimation() { return true; }
        };
        completeHandleClickOn(marblesEngine, 10);
        // assert ...
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.isLost()).isEqualTo(false);
        assertThat(marblesEngine.getSelected()).isEqualTo(10);
        assertThat(marblesEngine.getStage()[marblesEngine.getSelected()]).isEqualTo(1);

        // go on...
        // act...
        completeHandleClickOn(marblesEngine, 4);
        // assert ...
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.isLost()).isEqualTo(false);
        assertThat(marblesEngine.getSelected()).isEqualTo(-1);
        assertThat(marblesEngine.getStage()[0]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[1]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[2]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[3]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[4]).isEqualTo(0);
    }

    @Test
    public void testAnimatedMove() throws Exception {
        int[] stage = new int[] {
                1, 1, 1, 1, 0,
                0, 0, 0, 0, 0,
                1, 1, 1, 2, 2,
                1, 1, 1, 2, 2,
                1, 1, 1, 2, 2
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 5, 0) {
            @Override boolean isTimeForAnimation() { return true; }
        };
        marblesEngine.handleClickOn(14);
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.getSelected()).isEqualTo(14);
        marblesEngine.handleClickOn(6);
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        assertThat(marblesEngine.getSelected()).isEqualTo(-1);
        assertThat(marblesEngine.isAnimating()).isEqualTo(true);
        assertThat(marblesEngine.getStage()[14]).isEqualTo(2);
        marblesEngine.animate();
        assertThat(marblesEngine.getStage()[14]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[9]).isEqualTo(2);
        marblesEngine.animate();
        assertThat(marblesEngine.getStage()[9]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[8]).isEqualTo(2);
        marblesEngine.animate();
        assertThat(marblesEngine.getStage()[8]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[7]).isEqualTo(2);
        marblesEngine.animate();
        assertThat(marblesEngine.getStage()[7]).isEqualTo(0);
        assertThat(marblesEngine.getStage()[6]).isEqualTo(2);
        assertThat(marblesEngine.isAnimating()).isEqualTo(false);
        assertThat(marblesEngine.getSelected()).isEqualTo(-1);
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
    }

    @Test
    public void testUndo() throws Exception {
        int[] stage = new int[] {
                1, 1, 1, 1, 0,
                0, 0, 0, 0, 0,
                0, 1, 1, 2, 2,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 5, 0) {
            @Override boolean isTimeForAnimation() { return true; }
        };
        completeHandleClickOn(marblesEngine, 14);
        completeHandleClickOn(marblesEngine, 6);
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
        marblesEngine.addMarbleIfNeeded();
        marblesEngine.addMarbleIfNeeded();
        marblesEngine.addMarbleIfNeeded();
        marblesEngine.addMarbleIfNeeded();
        marblesEngine.undo();
        // assert ...
        assertThat(marblesEngine.getStage()).isEqualTo(stage);
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(0);
    }

    @Test
    public void testThatNegativeUndoIsImpossible() throws Exception {
        int[] stage = new int[] {
                1, 1, 1, 1, 0,
                0, 0, 0, 0, 0,
                0, 1, 1, 2, 2,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        // act...
        final MarblesEngine marblesEngine = new MarblesEngine(stage, 5, 0) {
            @Override boolean isTimeForAnimation() { return true; }
        };
        marblesEngine.undo();
        // assert ...
        assertThat(marblesEngine.getStage()).isEqualTo(stage);
        assertThat(marblesEngine.getMarblesToAdd()).isEqualTo(1);
    }

    private void completeHandleClickOn(MarblesEngine marblesEngine, int where) {
        marblesEngine.handleClickOn(where);
        //noinspection StatementWithEmptyBody
        while (marblesEngine.animate());
    }

}
