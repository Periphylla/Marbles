package berlin.blick.marbles.fx;

import android.content.Context;
import android.media.AudioManager;

public class BaseFxHandler implements AudioManager.OnAudioFocusChangeListener {

    public BaseFxHandler(Context context) {

    }

    @Override
    public void onAudioFocusChange(int focusChange) { }

    public void move() { }

    public void deselect() { }

    public void success() { }

    public void lost() { }
}
