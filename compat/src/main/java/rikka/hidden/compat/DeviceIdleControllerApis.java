package rikka.hidden.compat;

import static rikka.hidden.compat.Services.deviceIdleController;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;

@SuppressLint("LongLogTag")
@RequiresApi(Build.VERSION_CODES.M)
public class DeviceIdleControllerApis {

    private static final String TAG = "DeviceIdleControllerApis";

    public static void addPowerSaveWhitelistApp(@NonNull String packageName) throws RemoteException {
        deviceIdleController.get().addPowerSaveWhitelistApp(packageName);
    }

    public static void addPowerSaveWhitelistAppNoThrow(@NonNull String packageName) {
        try {
            addPowerSaveWhitelistApp(packageName);
        } catch (Throwable e) {
            Log.e(TAG, "addPowerSaveWhitelistAppNoThrow: " + e.getMessage(), e);
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    public static int addPowerSaveWhitelistApps(@NonNull List<String> packageNames) throws RemoteException {
        return deviceIdleController.get().addPowerSaveWhitelistApps(packageNames);
    }

    @RequiresApi(Build.VERSION_CODES.R)
    public static int addPowerSaveWhitelistAppsNoThrow(@NonNull List<String> packageNames) {
        try {
            return addPowerSaveWhitelistApps(packageNames);
        } catch (Throwable e) {
            Log.e(TAG, "addPowerSaveWhitelistAppsNoThrow: " + e.getMessage(), e);
            return -1;
        }
    }

    public static void removePowerSaveWhitelistApp(@NonNull String packageName) throws RemoteException {
        deviceIdleController.get().removePowerSaveWhitelistApp(packageName);
    }

    public static void removePowerSaveWhitelistAppNoThrow(@NonNull String packageName) {
        try {
            removePowerSaveWhitelistApp(packageName);
        } catch (Throwable e) {
            Log.e(TAG, "removePowerSaveWhitelistAppNoThrow: " + e.getMessage(), e);
        }
    }

    public static boolean isPowerSaveWhitelistExceptIdleApp(@NonNull String packageName) throws RemoteException {
        return deviceIdleController.get().isPowerSaveWhitelistExceptIdleApp(packageName);
    }

    public static boolean isPowerSaveWhitelistExceptIdleAppNoThrow(@NonNull String packageName) {
        try {
            return isPowerSaveWhitelistExceptIdleApp(packageName);
        } catch (Throwable e) {
            Log.e(TAG, "isPowerSaveWhitelistExceptIdleAppNoThrow: " + e.getMessage(), e);
            return false;
        }
    }

    public static boolean isPowerSaveWhitelistApp(@NonNull String packageName) throws RemoteException {
        return deviceIdleController.get().isPowerSaveWhitelistApp(packageName);
    }

    public static boolean isPowerSaveWhitelistAppNoThrow(@NonNull String packageName) {
        try {
            return isPowerSaveWhitelistApp(packageName);
        } catch (Throwable e) {
            Log.e(TAG, "isPowerSaveWhitelistAppNoThrow: " + e.getMessage(), e);
            return false;
        }
    }

    public static void addPowerSaveTempWhitelistApp(@NonNull String name, long duration, int userId, int reasonCode, String reason) throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            deviceIdleController.get().addPowerSaveTempWhitelistApp(name, duration, userId, reasonCode, reason);
        } else {
            deviceIdleController.get().addPowerSaveTempWhitelistApp(name, duration, userId, reason);
        }
    }

    public static void addPowerSaveTempWhitelistAppNoThrow(@NonNull String name, long duration, int userId, int reasonCode, String reason) {
        try {
            addPowerSaveTempWhitelistApp(name, duration, userId, reasonCode, reason);
        } catch (Throwable e) {
            Log.e(TAG, "addPowerSaveTempWhitelistAppNoThrow: " + e.getMessage(), e);
        }
    }
}
