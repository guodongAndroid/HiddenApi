package android.net;

import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/12
 */
public class EthernetManager {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int ETHERNET_STATE_DISABLED = 0;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int ETHERNET_STATE_ENABLED  = 1;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int STATE_ABSENT = 0;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int STATE_LINK_DOWN = 1;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int STATE_LINK_UP = 2;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int ROLE_NONE = 0;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int ROLE_CLIENT = 1;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final int ROLE_SERVER = 2;
}
