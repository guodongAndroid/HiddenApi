package android.net;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

/**
 * Created by guodongAndroid on 2025/8/13
 */
public interface IConnectivityManager extends IInterface {

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @NonNull
    Network[] getAllNetworks()
             throws RemoteException;

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @Nullable
    LinkProperties getLinkProperties(Network network)
             throws RemoteException;

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @Nullable
    NetworkCapabilities getNetworkCapabilities(Network network)
             throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.R)
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @Nullable
    NetworkCapabilities getNetworkCapabilities(Network network, String callingPackageName)
             throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.S)
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @Nullable
    NetworkCapabilities getNetworkCapabilities(Network network, String callingPackageName, @Nullable String callingAttributionTag)
             throws RemoteException;

    abstract class Stub extends Binder implements IConnectivityManager {
        public static IConnectivityManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
