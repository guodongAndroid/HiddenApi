package android.view;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/7
 */
@RefineAs(Display.class)
public class DisplayHidden {

    public DisplayAddress getAddress() {
        throw new RuntimeException();
    }
}
