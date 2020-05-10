package berlin.blick.marbles.engine;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MarblesViewIntegrationTest {

    @Test
    public void testDetermineDirection() {
        Context appContext = getInstrumentation().getTargetContext();
        final MarblesView view = new MarblesView(appContext);
        assertThat(view.determineDirection(100, 50), is(MarblesView.CanvasDirection.HORIZONTAL));
        assertThat(view.determineDirection(100, 150), is(MarblesView.CanvasDirection.VERTICAL));
    }
}
