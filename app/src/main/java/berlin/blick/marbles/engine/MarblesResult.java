package berlin.blick.marbles.engine;

public class MarblesResult {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int DIAGONAL_UPWARDS = 2;
    public static final int DIAGONAL_DOWNWARDS = 3;

    private static final int INVALID = -1;

    private final int _direction;
    private int _position;

    public static MarblesResult noSuccess() {
        return new MarblesResult(INVALID, INVALID);
    }

    public MarblesResult(int position, int direction) {
        _position = position;
        _direction = direction;
    }

    public boolean isSuccess() {
        return _position > INVALID;
    }

    public int getDirection() {
        return _direction;
    }

    public int getPosition() {
        return _position;
    }
    
    public boolean next(int width) {
        switch (_direction) {
            case HORIZONTAL: _position = (_position + 1 > width * width - 1 || (_position + 1) / width > _position / width) ? INVALID : _position + 1; break;
            case VERTICAL: _position = (_position + width > width * width - 1) ? INVALID : _position + width; break;
            case DIAGONAL_UPWARDS: _position = (_position + (width - 1) >= width * width || (_position + (width - 1)) % width > _position % width) ? INVALID : _position + (width - 1); break;
            case DIAGONAL_DOWNWARDS: _position = (_position + (width + 1) > width * width - 1 || (_position + (width + 1)) % width < _position % width) ? INVALID : _position + (width + 1); break;
            default: throw new IllegalStateException("Cannot handle direction: " + _direction);
        }
        return _position != INVALID;
    }
}
