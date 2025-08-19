package android.os;

import android.content.IIntentSender;
import android.net.InterfaceConfiguration;

/**
 * Created by guodongAndroid on 2025/8/19
 */
public interface INetworkManagementService extends IIntentSender {

    String[] listInterfaces() throws RemoteException;

    InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException;

    void setInterfaceConfig(String iface, InterfaceConfiguration cfg) throws RemoteException;

    void setInterfaceDown(String iface) throws RemoteException;

    void setInterfaceUp(String iface) throws RemoteException;

    abstract class Stub extends Binder implements INetworkManagementService {
        public static INetworkManagementService asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }
    }
}
