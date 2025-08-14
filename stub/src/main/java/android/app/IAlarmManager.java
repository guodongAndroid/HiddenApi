package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by guodongAndroid on 2025/8/14
 */
public interface IAlarmManager extends IInterface {

    boolean setTime(long millis) throws RemoteException;

    void setTimeZone(String zone) throws RemoteException;

    abstract class Stub extends Binder implements IAlarmManager {
        public static IAlarmManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
