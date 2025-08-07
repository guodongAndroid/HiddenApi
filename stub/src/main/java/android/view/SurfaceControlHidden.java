package android.view;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/7
 */
@RefineAs(SurfaceControl.class)
public class SurfaceControlHidden {

    public static int BUILT_IN_DISPLAY_ID_MAIN;
    public static int POWER_MODE_OFF;
    public static int POWER_MODE_DOZE;
    public static int POWER_MODE_NORMAL;
    public static int POWER_MODE_DOZE_SUSPEND;

    @RequiresApi(Build.VERSION_CODES.P)
    public static int POWER_MODE_ON_SUSPEND;

    public static IBinder getBuiltInDisplay(int builtInDisplayId) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public static IBinder getInternalDisplayToken() {
        throw new RuntimeException();
    }

    public static Bitmap screenshot(int width, int height) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.P)
    public static Bitmap screenshot(Rect sourceCrop, int width, int height, int rotation) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public static ScreenshotHardwareBuffer captureDisplay(DisplayCaptureArgs captureArgs) {
        throw new RuntimeException();
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public static IBinder getPhysicalDisplayToken(long physicalDisplayId) {
        throw new RuntimeException();
    }

    public static void setDisplayPowerMode(IBinder displayToken, int mode) {
        throw new RuntimeException();
    }

    public static class ScreenshotHardwareBuffer {
        public ScreenshotHardwareBuffer(HardwareBuffer hardwareBuffer, ColorSpace colorSpace,
                                        boolean containsSecureLayers, boolean containsHdrLayers) {
            throw new RuntimeException();
        }

        public Bitmap asBitmap() {
            throw new RuntimeException();
        }
    }

    public static class CaptureArgs {

        private CaptureArgs(CaptureArgs.Builder<? extends CaptureArgs.Builder<?>> builder) {
        }

        public static class Builder<T extends CaptureArgs.Builder<T>> {

            public CaptureArgs build() {
                throw new RuntimeException();
            }

            public T setPixelFormat(int pixelFormat) {
                throw new RuntimeException();
            }

            public T setSourceCrop(@Nullable Rect sourceCrop) {
                throw new RuntimeException();
            }

            public T setFrameScale(float frameScale) {
                throw new RuntimeException();
            }
        }
    }

    public static class DisplayCaptureArgs extends CaptureArgs {
        private DisplayCaptureArgs(DisplayCaptureArgs.Builder builder) {
            super(builder);
            throw new RuntimeException();
        }

        public static class Builder extends CaptureArgs.Builder<DisplayCaptureArgs.Builder> {
            public DisplayCaptureArgs build() {
                throw new RuntimeException();
            }

            public Builder(IBinder displayToken) {
                throw new RuntimeException();
            }

            public DisplayCaptureArgs.Builder setSize(int width, int height) {
                throw new RuntimeException();
            }
        }
    }
}
