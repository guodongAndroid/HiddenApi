package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by guodongAndroid on 2025/8/7
 */
public interface IPackageDeleteObserver2 extends IInterface {

    void onPackageDeleted(String basePackageName, int returnCode, String msg)
            throws RemoteException;

    abstract class Stub extends Binder implements IPackageDeleteObserver2 {

        public static IPackageDeleteObserver2 asInterface(IBinder obj) {
            throw new RuntimeException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException();
        }
    }
}
