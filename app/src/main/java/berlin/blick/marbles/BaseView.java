package berlin.blick.marbles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import berlin.blick.marbles.fx.BaseFxHandler;

@SuppressWarnings("UnusedDeclaration")
public abstract class BaseView extends View {

    private static final Rect INFO_SRC = new Rect(0, 0, 20, 20);

    private float _touchX;
    private float _touchY;
    private boolean _touched = false;
    private long _startTime;
    private long _lastTime;
    private Bitmap _info;
    private Rect _infoDst;

    private boolean _lastCalculateSuccessful;
    private boolean _init;
    private BaseFxHandler _fxHandler;

    public BaseView(Context context) {
        super(context);
    }

    public void setup(Startup activity) {
        try {
            _startTime = System.currentTimeMillis();
            onSetup(activity);
            _info = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.info));
        } catch (Exception ex ) {
            System.out.println("Failed to setup view " + getClass().getSimpleName() + "!!!");
            ex.printStackTrace();
        }
    }

    public Rect prepareDraw() {
        Rect redraw = null;
        try {
            boolean lastCalculateSuccessful = _lastCalculateSuccessful;
            _lastCalculateSuccessful = false;
            long now = System.nanoTime();
            final long freeMem = Runtime.getRuntime().freeMemory();
            final long maxMem = Runtime.getRuntime().maxMemory();
//            Log.d("onDraw", "time since last call: " + (now - _lastTime) + "ms. (freeMem: " + freeMem + ", maxMem: " + maxMem + ")");
            if (_init && now - _lastTime > 3000) {
                redraw = calculate(now, lastCalculateSuccessful);
                _lastTime = now;
            }
            _lastCalculateSuccessful = true;
        } catch (Exception e) {
            _lastCalculateSuccessful = false;
            e.printStackTrace();
        }
        return redraw;
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!_init) {
            init();
            _infoDst = new Rect(getWidth() - 23, getHeight() - 23, getWidth() - 1, getHeight() - 1);
            _init = true;
        }
        doDraw(canvas);
        canvas.drawBitmap(_info, INFO_SRC, _infoDst, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            _touched = false;
            onFingerUp(event);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _touched = true;
            _touchX = event.getX();
            _touchY = event.getY();
//            if (_infoDst.contains((int) _touchX, (int) _touchY)) {
//                _activity.switchToPreferences();
//            }
            onFingerDown(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            onFingerMove(event);
        }
        Log.i("engine", this.getClass().getSimpleName() + ".onTouch[" + event.getAction()  + "]: (" + _touchX + "x" + _touchY + ")");
        return super.onTouchEvent(event);
    }

    protected float getTouchX() {
        return _touchX;
    }

    protected float getTouchY() {
        return _touchY;
    }

    protected boolean isTouched() {
        return _touched;
    }

    protected long getStartTime() {
        return _startTime;
    }

    protected long getLastTime() {
        return _lastTime;
    }

    protected abstract void doDraw(Canvas canvas);

    protected void init() {}

    protected void onFingerDown(MotionEvent event) { }

    protected void onFingerUp(MotionEvent event) { }

    protected void onFingerMove(MotionEvent event) { }

    protected Rect calculate(long now, boolean lastCalculateSuccessful) { return new Rect(0, 0, getWidth(), getHeight()); }

    protected abstract void onSetup(Startup activity) throws Exception;

    public void setFxHandler(BaseFxHandler fxHandler) {
        _fxHandler = fxHandler;
    }

    protected BaseFxHandler getFxHandler() {
        return _fxHandler;
    }
}
