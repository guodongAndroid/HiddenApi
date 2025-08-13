package rikka.hidden.compat.adapter;

import android.net.EthernetNetworkManagementException;
import android.net.INetworkInterfaceOutcomeReceiver;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by guodongAndroid on 2025/8/13
 */
public class NetworkInterfaceOutcomeReceiverAdapter extends INetworkInterfaceOutcomeReceiver.Stub {
    @Override
    public void onResult(String iface) {

    }

    @Override
    public void onError(EthernetNetworkManagementException e) {

    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        try {
            return super.onTransact(code, data, reply, flags);
        } catch (RemoteException e) {
            return true;
        }
    }
}
