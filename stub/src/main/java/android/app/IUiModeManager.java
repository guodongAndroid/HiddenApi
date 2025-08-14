package android.app;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/14
 */
public interface IUiModeManager extends IInterface {

    @RequiresApi(Build.VERSION_CODES.R)
    boolean setNightModeActivated(boolean active) throws RemoteException;

    abstract class Stub extends Binder implements IUiModeManager {
        public static IUiModeManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
