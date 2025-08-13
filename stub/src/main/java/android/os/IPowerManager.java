package android.os;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/13
 */
public interface IPowerManager extends IInterface {

    void wakeUp(long time) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.M)
    void wakeUp(long time, String reason, String opPackageName) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.Q)
    void wakeUp(long time, int reason, String details, String opPackageName) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    void wakeUpWithDisplayId(long time, int reason, String details, String opPackageName, int displayId) throws RemoteException;

    void goToSleep(long time, int reason, int flags) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    void goToSleepWithDisplayId(int displayId, long time, int reason, int flags) throws RemoteException;

    void reboot(boolean confirm, String reason, boolean wait) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.N)
    void rebootSafeMode(boolean confirm, boolean wait) throws RemoteException;

    void shutdown(boolean confirm, boolean wait) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.N)
    void shutdown(boolean confirm, String reason, boolean wait) throws RemoteException;

    abstract class Stub extends Binder implements IPowerManager {
        public static IPowerManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
