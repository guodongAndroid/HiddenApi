package android.content.pm;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by guodongAndroid on 2025/8/7
 */
public interface IPackageInstallObserver2 extends IInterface {

    void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras)
            throws RemoteException;

    abstract class Stub extends Binder implements IPackageInstallObserver2 {

        public static IPackageInstallObserver2 asInterface(IBinder obj) {
            throw new RuntimeException();
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException();
        }
    }
}
