package berlin.blick.marbles.engine;


import berlin.blick.marbles.util.AndroidHelper;

public class MarblesFieldAgent implements DijkstraCalculator.DijkstraAgent {

    private static final int[] EMPTY = new int[0];
    private static final int[] RESULTS = new int[4];

    private final int[] _stage;
    private final int _width;
    private int _size;

    public MarblesFieldAgent(int[] stage, int width) {
        _stage = stage;
        _width = width;
    }

    @Override
    public int getDistance(int from, int to) {
        return 1; // ...is always 1 for marbles
    }

    @Override
    public int[] getNeighbours(int current) {
        reset();
        if (current - _width >= 0 && _stage[current - _width] == 0) {
            fillValue(current - _width);
        }
        if (current - 1 >= 0 && (current - 1) / _width == current / _width && _stage[current - 1] == 0) {
            fillValue(current - 1);
        }
        if ((current + 1) / _width == current / _width && _stage[current + 1] == 0) {
            fillValue(current + 1);
        }
        if (current + _width < _width * _width && _stage[current + _width] == 0) {
            fillValue(current + _width);
        }
        return (_size == 0) ? EMPTY : AndroidHelper.arrayCopy(RESULTS, 0, _size);
    }

    private void reset() {
        _size = 0;
        RESULTS[0] = -1; RESULTS[1] = -1; RESULTS[2] = -1; RESULTS[3] = -1;
    }

    private void fillValue(int value) {
        RESULTS[_size] = value;
        _size++;
    }
}
