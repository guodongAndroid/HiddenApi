package rikka.hidden.compat;

import static rikka.hidden.compat.Services.uiModeManager;

import android.app.ActivityThread;
import android.app.ContextImpl;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.RemoteException;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/14
 */
public class UiModeManagerApis {

    @RequiresApi(Build.VERSION_CODES.R)
    public static boolean setNightModeActivated(boolean active) throws RemoteException {
        return uiModeManager.get().setNightModeActivated(active);
    }

    @RequiresApi(Build.VERSION_CODES.R)
    public static boolean setNightModeActivatedNoThrow(boolean active) {
        try {
            return setNightModeActivated(active);
        } catch (Throwable ignore) {
            return false;
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    public static boolean isNightModeActivated() {
        int mode = Resources.getSystem().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_YES;
        return mode != 0;
    }
}
