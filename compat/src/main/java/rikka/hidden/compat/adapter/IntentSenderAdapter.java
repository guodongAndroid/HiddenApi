package rikka.hidden.compat.adapter;

import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by guodongAndroid on 2025/8/8
 */
public class IntentSenderAdapter extends IIntentSender.Stub {
    @Override
    public void send(int code, Intent intent, String resolvedType, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {

    }

    @Override
    public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {

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
