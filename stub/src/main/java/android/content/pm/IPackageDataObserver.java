package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/**
 * Created by guodongAndroid on 2025/8/7
 */
public interface IPackageDataObserver extends IInterface {

    void onRemoveCompleted(String packageName, boolean succeeded)
            throws android.os.RemoteException;

    abstract class Stub extends Binder implements IPackageDataObserver {

        public static IPackageDataObserver asInterface(IBinder obj) {
            throw new RuntimeException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException();
        }
    }
}
