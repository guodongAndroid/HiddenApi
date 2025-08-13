package rikka.hidden.compat.adapter;

import android.net.IEthernetServiceListener;
import android.net.IpConfiguration;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by guodongAndroid on 2025/8/13
 */
public class EthernetServiceListenerAdapter extends IEthernetServiceListener.Stub {
    @Override
    public void onAvailabilityChanged(boolean isAvailable) {

    }

    @Override
    public void onAvailabilityChanged(String iface, boolean isAvailable) {

    }

    @Override
    public void onEthernetStateChanged(int state) {

    }

    @Override
    public void onInterfaceStateChanged(String iface, int state, int role, IpConfiguration configuration) {

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
