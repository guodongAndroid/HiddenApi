package android.net;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by guodongAndroid on 2025/8/7
 */
public class SntpClient {

    public boolean requestTime(String host, int timeout) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public boolean requestTime(String host, int timeout, Network network) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public boolean requestTime(String host, int port, int timeout, Network network) {
        throw new RuntimeException();
    }

    public boolean requestTime(InetAddress address, int port, int timeout) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public boolean requestTime(InetAddress address, int port, int timeout, Network network) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public long getClockOffset() {
        throw new RuntimeException();
    }

    public long getNtpTime() {
        throw new RuntimeException();
    }

    public long getNtpTimeReference() {
        throw new RuntimeException();
    }

    public long getRoundTripTime() {
        throw new RuntimeException();
    }

    @Nullable
    public InetSocketAddress getServerSocketAddress() {
        throw new RuntimeException();
    }
}
