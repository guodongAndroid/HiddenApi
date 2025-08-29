package android.hardware.usb;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/29
 */
@RefineAs(UsbManager.class)
public class UsbManagerHidden {

    public void grantPermission(UsbDevice device) {
        throw new UnsupportedOperationException();
    }

    public void grantPermission(UsbDevice device, int uid) {
        throw new UnsupportedOperationException();
    }

    public void grantPermission(UsbDevice device, String packageName) {
        throw new UnsupportedOperationException();
    }
}
