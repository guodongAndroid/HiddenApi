package rikka.hidden.compat;

import android.os.Build;
import android.os.IBinder;
import android.view.SurfaceControlHidden;

/**
 * Created by guodongAndroid on 2025/8/8
 */
public class SurfaceControlApis {

    public static void setDisplayPowerMode(int mode) {
        IBinder displayToken;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            // Java层无法获取到displayToken
            return;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            displayToken = SurfaceControlHidden.getInternalDisplayToken();
        } else {
            displayToken = SurfaceControlHidden.getBuiltInDisplay(SurfaceControlHidden.BUILT_IN_DISPLAY_ID_MAIN);
        }

        if (displayToken == null) {
            return;
        }

        SurfaceControlHidden.setDisplayPowerMode(displayToken, mode);
    }
}
