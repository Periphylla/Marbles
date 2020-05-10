package berlin.blick.marbles.engine;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import berlin.blick.marbles.R;
import berlin.blick.marbles.fx.BaseFxHandler;

public class MarblesFx extends BaseFxHandler {

    private MediaPlayer _dropPlayer;
    private MediaPlayer _bopPlayer;
    private MediaPlayer _boinkPlayer;
    private MediaPlayer _lostPlayer;

    public MarblesFx(Context context, ComponentName componentName) {
        super(context);
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        final int result = am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            am.unregisterMediaButtonEventReceiver(componentName);
            _dropPlayer = MediaPlayer.create(context, R.raw.drop);
            _boinkPlayer = MediaPlayer.create(context, R.raw.boink);
            _bopPlayer = MediaPlayer.create(context, R.raw.bop);
            _lostPlayer = MediaPlayer.create(context, R.raw.fail);
        }
    }

    @Override
    public void move() {
        if (_bopPlayer != null) {
            _bopPlayer.start();
        }
    }

    @Override
    public void deselect() {
        if (_boinkPlayer != null) {
            _boinkPlayer.start();
        }
    }

    @Override
    public void success() {
        if (_dropPlayer != null) {
            _dropPlayer.start();
        }
    }

    @Override
    public void lost() {
        if (_lostPlayer != null) {
            _lostPlayer.start();
            _lostPlayer = null;
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) { }
}
