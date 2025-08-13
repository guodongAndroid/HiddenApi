package android.net;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import androidx.annotation.RequiresApi;

import java.util.List;

/**
 * Created by guodongAndroid on 2025/8/12
 * <p>
 * <a href="https://cs.android.com/android/platform/superproject/+/android-7.1.2_r39:frameworks/base/core/java/android/net/IEthernetManager.aidl">7.1.2</a><p>
 * <a href="https://cs.android.com/android/platform/superproject/+/android-13.0.0_r84:packages/modules/Connectivity/framework-t/src/android/net/IEthernetManager.aidl">13</a>
 */
public interface IEthernetManager extends IInterface {

    IpConfiguration getConfiguration()
            throws RemoteException;

    void setConfiguration(IpConfigurationHidden config)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    boolean isAvailable()
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    void addListener(IEthernetServiceListener listener)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    void removeListener(IEthernetServiceListener listener)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.P)
    String[] getAvailableInterfaces()
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.P)
    IpConfiguration getConfiguration(String iface)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.P)
    void setConfiguration(String iface, IpConfiguration config)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.P)
    boolean isAvailable(String iface)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void setEthernetEnabled(boolean enabled)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void updateConfiguration(String iface, EthernetNetworkUpdateRequest request,
                             INetworkInterfaceOutcomeReceiver listener)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void connectNetwork(String iface, INetworkInterfaceOutcomeReceiver listener)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    void disconnectNetwork(String iface, INetworkInterfaceOutcomeReceiver listener)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    List<String> getInterfaceList()
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    void enableInterface(String iface, INetworkInterfaceOutcomeReceiver listener)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    void disableInterface(String iface, INetworkInterfaceOutcomeReceiver listener)
            throws RemoteException;

    abstract class Stub extends Binder implements IEthernetManager {
        public static IEthernetManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}
