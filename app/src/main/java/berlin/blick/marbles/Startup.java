package berlin.blick.marbles;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import berlin.blick.marbles.fx.BaseFxHandler;

public abstract class Startup extends AppCompatActivity {

    private BaseView _mainView;
    private MainLoop _mainLoop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        if (_mainView == null) {
            _mainView = createView(context);
            _mainView.setFxHandler(createFxHandler(context));
            _mainView.setup(this);
            setContentView(_mainView);
        }
    }

    class MainLoop extends Thread {
        private boolean _stopped;

        @Override
        public void run() {
            Log.w("Main", "Main Thread started.");
            while (!_stopped) {
                final CountDownLatch waitForViewRedraw = new CountDownLatch(1);
                final Rect drawRect = _mainView.prepareDraw();
                if (drawRect != null) {
                    runOnUiThread(new Runnable() { @Override public void run() {
                        _mainView.invalidate(drawRect);
                        waitForViewRedraw.countDown();
                    }});
                } else {
                    waitForViewRedraw.countDown();
                }
                try {
                    Thread.sleep(20);
                    waitForViewRedraw.await(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    _stopped = true;
                }
            }
            Log.w("Main", "Main Thread finished.");
        }

        public void signalStop() {
            _stopped = true;
        }
    }

    protected abstract BaseView createView(Context context);

    protected BaseFxHandler createFxHandler(Context context) {
        return new BaseFxHandler(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _mainLoop = new MainLoop();
        _mainLoop.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        _mainLoop.signalStop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        _mainLoop.signalStop();
        super.onStop();
    }
}
