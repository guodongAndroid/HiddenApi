package android.net;

import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by guodongAndroid on 2025/8/19
 */
public class InterfaceConfiguration {

    public static final String FLAG_UP = "up";
    public static final String FLAG_DOWN = "down";

    public Iterable<String> getFlags() {
        throw new UnsupportedOperationException();
    }

    public boolean hasFlag(String flag) {
        throw new UnsupportedOperationException();
    }

    public void clearFlag(String flag) {
        throw new UnsupportedOperationException();
    }

    public void setFlag(String flag) {
        throw new UnsupportedOperationException();
    }

    public void setInterfaceUp() {
        throw new UnsupportedOperationException();
    }

    public void setInterfaceDown() {
        throw new UnsupportedOperationException();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public void ignoreInterfaceUpDownStatus() {
        throw new UnsupportedOperationException();
    }

    public LinkAddress getLinkAddress() {
        throw new UnsupportedOperationException();
    }

    public void setLinkAddress(LinkAddress addr) {
        throw new UnsupportedOperationException();
    }

    public String getHardwareAddress() {
        throw new UnsupportedOperationException();
    }

    public void setHardwareAddress(String hwAddr) {
        throw new UnsupportedOperationException();
    }

    public boolean isActive() {
        throw new UnsupportedOperationException();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public boolean isUp() {
        throw new UnsupportedOperationException();
    }
}
