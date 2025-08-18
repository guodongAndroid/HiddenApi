package com.android.server.display;

import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/18
 */
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class DisplayControl {

    public static long[] getPhysicalDisplayIds() {
        throw new UnsupportedOperationException();
    }

    public static IBinder getPhysicalDisplayToken(long physicalDisplayId) {
        throw new UnsupportedOperationException();
    }
}
