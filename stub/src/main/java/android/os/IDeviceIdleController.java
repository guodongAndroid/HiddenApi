package android.os;

import androidx.annotation.RequiresApi;

import java.util.List;

@RequiresApi(Build.VERSION_CODES.M)
public interface IDeviceIdleController extends IInterface {

    void addPowerSaveWhitelistApp(String name) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.R)
    int addPowerSaveWhitelistApps(List<String> packageNames) throws RemoteException;

    void removePowerSaveWhitelistApp(String name) throws RemoteException;

    boolean isPowerSaveWhitelistExceptIdleApp(String name) throws RemoteException;

    boolean isPowerSaveWhitelistApp(String name) throws RemoteException;

    void addPowerSaveTempWhitelistApp(String name, long duration, int userId, String reason) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.S)
    void addPowerSaveTempWhitelistApp(String name, long duration, int userId, int reasonCode, String reason) throws RemoteException;

    abstract class Stub extends Binder implements IDeviceIdleController {

        public static IDeviceIdleController asInterface(IBinder obj) {
            throw new RuntimeException("STUB");
        }
    }
}
