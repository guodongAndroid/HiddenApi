package android.net;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/12
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
public interface IEthernetServiceListener extends IInterface {

    void onAvailabilityChanged(boolean isAvailable);

    @RequiresApi(Build.VERSION_CODES.P)
    void onAvailabilityChanged(String iface, boolean isAvailable);

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void onEthernetStateChanged(int state);

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void onInterfaceStateChanged(String iface, int state, int role,
                                 IpConfiguration configuration);

    abstract class Stub extends Binder implements IEthernetServiceListener {
        public static IEthernetServiceListener asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
