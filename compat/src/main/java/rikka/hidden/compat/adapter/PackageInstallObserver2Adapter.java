package rikka.hidden.compat.adapter;

import android.content.pm.IPackageInstallObserver2;
import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by guodongAndroid on 2025/8/7
 */
public class PackageInstallObserver2Adapter extends IPackageInstallObserver2.Stub {

    @Override
    public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) throws RemoteException {

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
