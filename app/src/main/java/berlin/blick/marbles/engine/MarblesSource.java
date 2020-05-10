package berlin.blick.marbles.engine;

public class MarblesSource {
    private int _pending;
    private final int _marblesToAdd;
    private final int _differentColors;
    private final int[] _nextColors;

    public MarblesSource(int marblesToAdd, int differentColors) {
        _marblesToAdd = marblesToAdd;
        _differentColors = differentColors;
        _pending = _marblesToAdd;
        _nextColors = new int[_marblesToAdd];
        generateNewColors();
    }

    public void reset() {
        _pending = 0;
        generateNewColors();
    }

    public int getPending() {
        return _pending;
    }

    public int next() {
        final int nextColor = _nextColors[_marblesToAdd - _pending];
        _nextColors[_marblesToAdd - _pending] = 0;
        _pending--;
        if (_pending == 0) {
            generateNewColors();
        }
        return nextColor;
    }

    public boolean isPending() {
        return _pending > 0;
    }


    public void approve() {
        _pending = _marblesToAdd;
    }

    public int[] getNextColors() {
        return _nextColors;
    }

    private void generateNewColors() {
        for (int i = 0; i < _marblesToAdd; i++) {
            _nextColors[i] = randomColor();
        }
    }

    private int randomColor() {
        return (int) (Math.random() * _differentColors) + 1;
    }
}
