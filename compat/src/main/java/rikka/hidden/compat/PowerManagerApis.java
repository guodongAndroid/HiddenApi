package rikka.hidden.compat;

import static rikka.hidden.compat.Services.powerManager;

import android.app.ActivityThread;
import android.content.Context;
import android.content.ContextHidden;
import android.os.Build;
import android.os.IPowerManager;
import android.os.PowerManagerHidden;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import dev.rikka.tools.refine.Refine;

/**
 * Created by guodongAndroid on 2025/8/13
 */
public class PowerManagerApis {

    public static void wakeUp(long time) throws RemoteException {
        IPowerManager manager = powerManager.get();
        Context context = ActivityThread.systemMain().getSystemContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            manager.wakeUp(time, PowerManagerHidden.WAKE_REASON_UNKNOWN, "wakeUp", context.getOpPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.wakeUp(time, "wakeUp", Refine.<ContextHidden>unsafeCast(context).getOpPackageName());
        } else {
            manager.wakeUp(time);
        }
    }

    public static void wakeUpNoThrow(long time) {
        try {
            wakeUp(time);
        } catch (RemoteException ignore) {
        }
    }

    public static void goToSleep(long time) throws RemoteException {
        powerManager.get().goToSleep(time, PowerManagerHidden.GO_TO_SLEEP_REASON_APPLICATION, 0);
    }

    public static void goToSleepNoThrow(long time) {
        try {
            goToSleep(time);
        } catch (RemoteException ignore) {
        }
    }

    public static void reboot(@Nullable String reason) throws RemoteException {
        powerManager.get().reboot(false, reason, true);
    }

    public static void rebootNoThrow(@Nullable String reason) {
        try {
            reboot(reason);
        } catch (RemoteException ignore) {
        }
    }

    public static void shutdown(@Nullable String reason) throws RemoteException {
        IPowerManager manager = powerManager.get();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager.shutdown(false, reason, true);
        } else {
            manager.shutdown(false, true);
        }
    }

    public static void shutdownNoThrow(@Nullable String reason) {
        try {
            shutdown(reason);
        } catch (RemoteException ignore) {
        }
    }
}
