package rikka.hidden.compat;

import static rikka.hidden.compat.Services.alarmManager;

import android.Manifest;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

/**
 * Created by guodongAndroid on 2025/8/14
 */
public class AlarmManagerApis {

    @RequiresPermission(Manifest.permission.SET_TIME)
    public static boolean setTime(long millis) throws RemoteException {
        return alarmManager.get().setTime(millis);
    }

    @RequiresPermission(Manifest.permission.SET_TIME)
    public static boolean setTimeNoThrow(long millis) {
        try {
            return setTime(millis);
        } catch (RemoteException | SecurityException e) {
            return false;
        }
    }

    @RequiresPermission(Manifest.permission.SET_TIME_ZONE)
    public static boolean setTimeZone(@NonNull String timeZone) throws RemoteException {
        alarmManager.get().setTimeZone(timeZone);
        return true;
    }

    @RequiresPermission(Manifest.permission.SET_TIME_ZONE)
    public static boolean setTimeZoneNoThrow(@NonNull String timeZone) {
        try {
            return setTimeZone(timeZone);
        } catch (RemoteException | SecurityException e) {
            return false;
        }
    }
}
