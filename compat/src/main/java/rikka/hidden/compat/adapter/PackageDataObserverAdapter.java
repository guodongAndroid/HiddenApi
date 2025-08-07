package rikka.hidden.compat.adapter;

import android.content.pm.IPackageDataObserver;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by guodongAndroid on 2025/8/7
 */
public class PackageDataObserverAdapter extends IPackageDataObserver.Stub {

    @Override
    public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {

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
