package berlin.blick.marbles.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

import berlin.blick.marbles.BaseView;
import berlin.blick.marbles.R;
import berlin.blick.marbles.Startup;

public class MarblesView extends BaseView {
    
    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;
    private static final int AREA = WIDTH * HEIGHT;
    private static final Paint TEXT = makeColor(Color.BLACK);
    private static final Paint LINE = makeColor(Color.BLACK);
    private static final Paint WHITE = makeColor(Color.WHITE);
    private static final Paint GRAY = makeColor(Color.DKGRAY);

    private Bitmap _lostPicture;
    private Bitmap _undo;
    private Rect _undoPosition;
    private MarblesEngine _marblesEngine;
    private final Map<Integer, Paint> _colors = new HashMap<>();
    private float _size;
    private int _clickedPosition = -1;
    private boolean _forceRedraw = false;
    double _whiteMark = 0.6;

    public enum CanvasDirection {
        HORIZONTAL,
        VERTICAL
    }

    public MarblesView(Context context) {
        super(context);
    }

    @Override
    protected void onSetup(Startup activity) {
        _lostPicture = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.momo));
        _undo = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.undo));
        _undoPosition = new Rect(0,0,0,0);
        _colors.put(1, makeColor(Color.DKGRAY));
        _colors.put(2, makeColor(Color.BLUE));
        _colors.put(3, makeColor(Color.GREEN));
        _colors.put(4, makeColor(Color.RED));
        _colors.put(5, makeColor(Color.CYAN));
        _colors.put(6, makeColor(Color.YELLOW));
        TEXT.setAntiAlias(true);
        TEXT.setTextSize(30);
        LINE.setStyle(Paint.Style.STROKE);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")  // it is needed for circle alignment and i coul not find a way to not warn
    @Override
    protected void doDraw(Canvas canvas) {
        if (!_marblesEngine.isLost()) {
            final Paint background = new Paint();
            background.setColor(0xFFF3D673);
            background.setStyle(Paint.Style.FILL);
            canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), background);
            for (int c = 0; c <= WIDTH; c++) {
                canvas.drawLine(_size * c, 0, _size * c, _size * HEIGHT, LINE);
            }
            for (int c = 0; c <= HEIGHT; c++) {
                canvas.drawLine(0, _size * c, _size * WIDTH, _size * c, LINE);
            }
            int selectedIndex = _marblesEngine.getSelected();
            final int[] stage = _marblesEngine.getStage();
            final float radius = (_size / 2f);
            // draw the mail field
            for (int c = 0; c < AREA; c++) {
                final float posX = c % WIDTH * _size;
                final float posY = (c / HEIGHT) * _size;
                if (stage[c] > 0) {
                    canvas.drawCircle(posX + radius + 3, posY + radius + 3, radius - 3, GRAY);
                    canvas.drawCircle(posX + radius, posY + radius, radius - 1, _colors.get(stage[c]));
                    canvas.drawCircle(posX + radius, posY + radius, radius - 1, LINE);
                    final float offsetX = (float) ((selectedIndex == c) ? Math.sin(_whiteMark) * radius * 0.7 : Math.sin(0.6) * radius * 0.7);
                    final float offsetY = (float) ((selectedIndex == c) ? Math.cos(_whiteMark) * radius * 0.7 : Math.cos(0.6) * radius * 0.7);
                    canvas.drawCircle(posX + radius + offsetX, posY + radius + offsetY, radius / 5f, LINE);
                    canvas.drawCircle(posX + radius + offsetX, posY + radius + offsetY, radius / 5.1f, WHITE);
                }
            }
            final int score = _marblesEngine.getScore();
            Log.i("onDraw", "Writing: " + score + " at: " + _size * (HEIGHT + 2) + "x" + getWidth() / 2 + ".");
            final int[] nextColors = _marblesEngine.previewNextColors();
            final int countPreview = nextColors.length;
            if (determineDirection(canvas) == CanvasDirection.HORIZONTAL) {
                final float align = (_size * WIDTH) + 5;
                if (_marblesEngine.getOperations() > 0) {
                    canvas.drawText(Integer.toString(score), align, (getHeight() / 2) + (TEXT.getTextSize() / 2) - 80, TEXT);
                    _undoPosition.set((int) align, getHeight() - 42, (int) (align + _undo.getWidth()), getHeight() - 42 + _undo.getHeight());
                    canvas.drawBitmap(_undo, align, getHeight() - 42, null);
                }
                canvas.drawLine((int) align, getHeight() - (_size * 2), (int) align, getHeight() - (_size * (2 + countPreview)), LINE);
                canvas.drawLine((int) (align + _size), getHeight() - (_size * 2), (int) (align + _size), getHeight() - (_size * (2 + countPreview)), LINE);
                for (int i = 0; i < countPreview; i++) {
                    canvas.drawLine((int) align, getHeight() - (_size * (2 + i)), (int) (align + _size), getHeight() - (_size * (2 + i)), LINE);
                    final Paint paint = _colors.get(nextColors[i]);
                    if (paint != null) {
                        canvas.drawCircle(align + radius, getHeight() - (_size * (2 + i)) - radius, radius, paint);
                    }
                }
                canvas.drawLine((int) align, getHeight() - (_size * (2 + countPreview)), (int) (align + _size), getHeight() - (_size * (2 + countPreview)), LINE);
            } else {
                final float align = (_size * HEIGHT) + 10;
                if (_marblesEngine.getOperations() > 0) {
                    canvas.drawText(Integer.toString(score), (getWidth() / 2) + 80, align + TEXT.getTextSize(), TEXT);
                    _undoPosition.set(10, (int) align, 10 + _undo.getWidth(), (int) (align + _undo.getHeight()));
                    canvas.drawBitmap(_undo, 10, align, null);
                }
                canvas.drawLine((_size * 2), (int) align, (_size * (2 + countPreview)), (int) align, LINE);
                canvas.drawLine((_size * 2), (int) (align + _size), (_size * (2 + countPreview)), (int) (align + _size), LINE);
                for (int i = 0; i < countPreview; i++) {
                    canvas.drawLine((_size * (2 + i)), (int) align, (_size * (2 + i)), (int) (align + _size), LINE);
                    final Paint paint = _colors.get(nextColors[i]);
                    if (paint != null) {
                        canvas.drawCircle((_size * (2 + i)) + radius, align + radius, radius, paint);
                    }
                }
                canvas.drawLine((_size * (2 + countPreview)), (int) align, (_size * (2 + countPreview)), (int) (align + _size), LINE);
            }
        } else {
            Rect src = new Rect(0, 0, _lostPicture.getWidth(), _lostPicture.getHeight());
            Rect dst = new Rect(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(_lostPicture, src, dst, null);
        }
    }

    @Override
    protected Rect calculate(long now, boolean lastCalculateSuccessful) {
        final boolean redraw;
        if (_marblesEngine.isAnimating()) {
            _marblesEngine.animate();
            redraw = true;
        } else {
            redraw = _marblesEngine.addMarbleIfNeeded();
        }
        if (redraw || _forceRedraw) {
            _forceRedraw = false;
            return new Rect(0, 0, getWidth(), getHeight());
        }
        if (_clickedPosition >= 0 && _clickedPosition < AREA) {
            _marblesEngine.handleClickOn(_clickedPosition);
            _clickedPosition = -1;
            return super.calculate(now, lastCalculateSuccessful);
        }
        Log.d("engine", "No redraw, new count of marbles: " + _marblesEngine.getMarblesToAdd());
        if (_marblesEngine.getSelected() >= 0) {
            _whiteMark += 0.1;
            final int selected = _marblesEngine.getSelected();
            final float posX = selected % WIDTH * _size;
            final float posY = (selected / HEIGHT) * _size;
            return new Rect((int) posX, (int) posY, (int) (posX + _size) + 1, (int) (posY + _size) + 1);
        } else {
            _whiteMark = 0.6;
        }
        return null;
    }

    @Override
    protected void onFingerDown(MotionEvent event) {
        if (_undoPosition.contains((int) getTouchX(), (int) getTouchY())) {
            _marblesEngine.undo();
            _forceRedraw = true;
        } else {
            _clickedPosition = getSelectedPosition(event);
        }
    }

    @Override
    protected void init() {
        super.init();
        if (determineDirection() == CanvasDirection.HORIZONTAL) {
            _size = getHeight() / WIDTH;
        } else {
            _size = getWidth() / WIDTH;
        }
        _marblesEngine = new MarblesEngine(WIDTH, 6);
        _marblesEngine.registerFxHandler(getFxHandler());
    }

    private CanvasDirection determineDirection(Canvas canvas) {
        return determineDirection(canvas.getWidth(), canvas.getHeight());
    }

    private CanvasDirection determineDirection() {
        return determineDirection(getWidth(), getHeight());
    }

    CanvasDirection determineDirection(int width, int height) {
        return width - height > 0 ? CanvasDirection.HORIZONTAL : CanvasDirection.VERTICAL;
    }

    private static Paint makeColor(int color) {
        final Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    private int getSelectedPosition(MotionEvent event) {
        return  (int) (event.getX() / _size) + (int) (event.getY() / _size) * WIDTH;
    }
}
