package rikka.hidden.compat;

import android.app.ActivityThread;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbManagerHidden;

import dev.rikka.tools.refine.Refine;

/**
 * Created by guodongAndroid on 2025/8/29
 */
public class UsbManagerApis {

    private static final String TAG = "UsbManagerApis";

    public static void grantPermission(UsbDevice device) {
        getUsbManagerHidden().grantPermission(device);
    }

    public static void grantPermission(UsbDevice device, int uid) {
        getUsbManagerHidden().grantPermission(device, uid);
    }

    public static void grantPermission(UsbDevice device, String packageName) {
        getUsbManagerHidden().grantPermission(device, packageName);
    }

    private static UsbManagerHidden getUsbManagerHidden() {
        Context context = ActivityThread.currentApplication();
        UsbManager um = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        return Refine.unsafeCast(um);
    }
}
