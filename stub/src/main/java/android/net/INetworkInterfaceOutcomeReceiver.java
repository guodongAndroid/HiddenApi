package android.net;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/12
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
public interface INetworkInterfaceOutcomeReceiver extends IInterface {

    void onResult(String iface);
    void onError(EthernetNetworkManagementException e);

    abstract class Stub extends Binder implements INetworkInterfaceOutcomeReceiver {
        public static INetworkInterfaceOutcomeReceiver asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
