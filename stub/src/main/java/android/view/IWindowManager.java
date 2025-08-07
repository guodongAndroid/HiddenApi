package android.view;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.window.ScreenCapture;

import androidx.annotation.RequiresApi;

import com.android.internal.policy.IKeyguardLockedStateListener;

public interface IWindowManager extends IInterface {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void addKeyguardLockedStateListener(IKeyguardLockedStateListener listener) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void removeKeyguardLockedStateListener(IKeyguardLockedStateListener listener) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    void captureDisplay(int displayId, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener listener)
            throws android.os.RemoteException;

    abstract class Stub extends Binder implements IWindowManager {

        public static IWindowManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
