package berlin.blick.marbles.engine;

import java.util.Optional;

import berlin.blick.marbles.fx.BaseFxHandler;

public class MarblesEngine {

    private final int _area;
    private final int[] _stage;
    private final int _width;
    private final MarblesFieldAgent _marblesFieldAgent;
    private final DijkstraCalculator _dijkstraCalculator;
    private final MarblesHistory _marblesHistory;
    private int _selected;
    private final MarblesSource _marblesSource;
    private int _score;
    private long _operations;
    private boolean _lost;
    private boolean _animating;
    private boolean _evaluationNeeded = false;
    private int[] _animationPath;
    private int _animationPosition;
    private long _lastAnimation;
    private BaseFxHandler _fxHandler;

    public MarblesEngine(int width, int differentColors) {
        this(new int[width * width], width, differentColors, 3);
    }
    
    // for test only
    MarblesEngine(int[] stage, int width, int differentColors) {
        this(stage, width, differentColors, 1);
    }

    private MarblesEngine(int[] stage, int width, int differentColors, int marblesToAdd) {
        _stage = stage;
        _width = width;
        _selected = -1;
        _score=  0;
        _marblesSource = new MarblesSource(marblesToAdd, differentColors);
        _lost = false;
        _area = width * width;
        _marblesFieldAgent = new MarblesFieldAgent(_stage, width);
        _dijkstraCalculator = new DijkstraCalculator(_area);
        _marblesHistory = new MarblesHistory(_stage);
    }

    public MarblesResult determineFullPattern() {
        MarblesResult result = null;
        for (int c = 0; c < _area; c++) {
            // determine -
            if (isHorizontal(c)) {
                result = new MarblesResult(c, MarblesResult.HORIZONTAL);
                break;
            }
            // determine |
            if (isVertical(c)) {
                result = new MarblesResult(c, MarblesResult.VERTICAL);
                break;
            }
            // determine /
            if (isDiagonalUpwards(c)) {
                result = new MarblesResult(c, MarblesResult.DIAGONAL_UPWARDS);
                break;
            }
            // determine \
            if (isDiagonalDownwards(c)) {
                result = new MarblesResult(c, MarblesResult.DIAGONAL_DOWNWARDS);
                break;
            }
        }
        return result != null ? result : MarblesResult.noSuccess();
    }

    public void handleClickOn(int clickedPosition) {
        int lastPosition = _selected;
        _selected = -1;
        if (lastPosition > -1 && _stage[clickedPosition] == 0) {
            // it's a move ...
            Optional.ofNullable(_fxHandler).ifPresent(BaseFxHandler::move);
            _marblesHistory.push(_stage);
            _operations++;
            _evaluationNeeded = true;
            _animationPath = _dijkstraCalculator.retrievePath(lastPosition, clickedPosition, _marblesFieldAgent);
            if (_animationPath.length > 0) {
                _animating = true;
                _animationPosition = 0;
            }
        } else if (lastPosition == clickedPosition) {
            // it's a deselect
            Optional.ofNullable(_fxHandler).ifPresent(BaseFxHandler::deselect);
        } else if (clickedPosition < _area && _stage[clickedPosition] > 0) {
            // it's just a selection
            _selected = clickedPosition;
        } else {
            // ignore that ... it's selection of nothing
        }
    }

    public boolean animate() {
        if (isTimeForAnimation()) {
            _lastAnimation = System.currentTimeMillis();
            if (isAnimating()) {
                int currentPos = _animationPath[_animationPosition];
                int nextPos = _animationPath[++_animationPosition];
                _stage[nextPos] = _stage[currentPos];
                _stage[currentPos] = 0;
                _animating = _animationPosition < _animationPath.length - 1;
            }
            final boolean animating = isAnimating();
            if (!animating && _evaluationNeeded) {
                evaluate();
            }
            return animating;
        }
        return false;
    }

    boolean isTimeForAnimation() {
        return _lastAnimation + 50 < System.currentTimeMillis();
    }

    private void evaluate() {
        final boolean success = dealWithCompletedPattern();
        _evaluationNeeded = false;
        if (!success) {
            _marblesSource.approve();
        }
    }

    public boolean addMarbleIfNeeded() {
        if (_marblesSource.isPending()) {
            addMarble();
            return true;
        }
        return false;
    }

    boolean isHorizontal(int position) {
        if (position % _width > _width - 5 || outOfRange(position, _width)) {
            return false;
        }
        boolean foundPattern = _stage[position] == _stage[position + 1];
        foundPattern &= _stage[position] == _stage[position + 2];
        foundPattern &= _stage[position] == _stage[position + 3];
        foundPattern &= _stage[position] == _stage[position + 4];
        return foundPattern;
    }

    boolean isVertical(int position) {
        if (position / _width > _width - 5 || outOfRange(position, _width)) {
            return false;
        }
        boolean foundPattern = _stage[position] == _stage[position + _width];
        foundPattern &= _stage[position] == _stage[position + (_width * 2)];
        foundPattern &= _stage[position] == _stage[position + (_width * 3)];
        foundPattern &= _stage[position] == _stage[position + (_width * 4)];
        return foundPattern;
    }

    boolean isDiagonalUpwards(int position) {
        if (position / _width > _width - 5 || position % _width < 4 || outOfRange(position, _width)) {
            return false;
        }
        boolean foundPattern = _stage[position] == _stage[position + (_width - 1)];
        foundPattern &= _stage[position] == _stage[position + ((_width - 1) * 2)];
        foundPattern &= _stage[position] == _stage[position + ((_width - 1) * 3)];
        foundPattern &= _stage[position] == _stage[position + ((_width - 1) * 4)];
        return foundPattern;
    }

    boolean isDiagonalDownwards(int position) {
        if (position / _width > _width - 5 || position % _width > _width - 5 || outOfRange(position, _width)) {
            return false;
        }
        boolean foundPattern = _stage[position] == _stage[position + (_width + 1)];
        foundPattern &= _stage[position] == _stage[position + ((_width + 1) * 2)];
        foundPattern &= _stage[position] == _stage[position + ((_width + 1) * 3)];
        foundPattern &= _stage[position] == _stage[position + ((_width + 1) * 4)];
        return foundPattern;
    }

    private void addMarble() {
        int nextPos = (int) (Math.random() * _area);
        boolean marbleSet = false;
        for (int c = nextPos; c < nextPos + _area; c++) {
            if (_stage[c % _area] == 0) {
                _stage[c % _area] = _marblesSource.next();
                marbleSet = true;
                if (dealWithCompletedPattern()) {
                    _marblesSource.reset();
                }
                break;
            }
        }
        _lost = !marbleSet;
        if (_lost) {
            Optional.ofNullable(_fxHandler).ifPresent(BaseFxHandler::lost);
        }
    }

    private boolean dealWithCompletedPattern() {
        final MarblesResult result = determineFullPattern();
        if (result.isSuccess()) {
            Optional.ofNullable(_fxHandler).ifPresent(BaseFxHandler::success);
            int color = _stage[result.getPosition()];
            int counter = 0;
            do {
                if (_stage[result.getPosition()] == color) {
                    _stage[result.getPosition()] = 0;
                    counter++;
                } else {
                    break;
                }
            } while (result.next(_width));
            _score += counter * counter;
            return true;
        }
        return false;
    }

    private boolean outOfRange(int position, int width) {
        return  position < 0 || position > width * width || _stage[position] == 0;
    }

    public int getSelected() {
        return _selected;
    }

    public int[] getStage() {
        return _stage;
    }

    public int getScore() {
        return _score;
    }

    public boolean isLost() {
        return _lost;
    }

    public int getMarblesToAdd() {
        return _marblesSource.getPending();
    }

    public boolean isAnimating() {
        return _animating;
    }

    public long getOperations() {
        return _operations;
    }

    public void undo() {
        if (_operations > 0) {
            final int[] oldStage = _marblesHistory.pop();
            //noinspection ManualArrayCopy somehow System.arrayCopy does not work on some phones, so we have to avoid it
            for (int i = 0; i < _stage.length; i++) {
                _stage[i] = oldStage[i];
            }
            _operations--;
            _marblesSource.reset();
        }
    }

    public int[] previewNextColors() {
        return _marblesSource.getNextColors();
    }

    public void registerFxHandler(BaseFxHandler fxHandler) {
        _fxHandler = fxHandler;
    }
}
