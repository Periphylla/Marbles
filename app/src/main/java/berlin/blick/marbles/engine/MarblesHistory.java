package berlin.blick.marbles.engine;

public class MarblesHistory {

    private final int[][] _history;
    private int _step = 0;

    public MarblesHistory(int[] stage) {
        assert stage != null;
        _history = new int[10][stage.length];
    }

    public void push(int[] stage) {
        for (int i = 0; i < stage.length; i++) {
            _history[_step][i] = stage[i];
        }
        increment();
    }

    public int[] pop() {
        decrement();
        return _history[_step];
    }

    private void increment() {
        _step = (_step < 9) ? _step + 1 : 0;
    }

    private void decrement() {
        _step = (_step > 0) ? _step - 1 : 9;
    }

    int getStep() {
        return _step;
    }
}
