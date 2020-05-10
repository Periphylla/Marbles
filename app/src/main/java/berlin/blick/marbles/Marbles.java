package berlin.blick.marbles;

import android.content.Context;
import android.os.Bundle;

import berlin.blick.marbles.engine.MarblesFx;
import berlin.blick.marbles.engine.MarblesView;
import berlin.blick.marbles.fx.BaseFxHandler;

public class Marbles extends Startup {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseView createView(Context context) {
        return new MarblesView(context);
    }

    @Override
    protected BaseFxHandler createFxHandler(Context context) {
        return new MarblesFx(context, getComponentName());
    }
}
