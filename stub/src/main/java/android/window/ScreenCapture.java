package android.window;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.function.ObjIntConsumer;

/**
 * Created by guodongAndroid on 2025/8/7
 */
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class ScreenCapture {

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
        private DisplayCaptureArgs(Builder builder) {
            super(builder);
            throw new RuntimeException();
        }

        public static class Builder extends CaptureArgs.Builder<Builder> {
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

    public static class ScreenCaptureListener implements Parcelable {
        public ScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> consumer) {
        }

        private ScreenCaptureListener(Parcel in) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
        }

        public static final Parcelable.Creator<ScreenCaptureListener> CREATOR =
                new Parcelable.Creator<ScreenCaptureListener>() {
                    @Override
                    public ScreenCaptureListener createFromParcel(Parcel in) {
                        return new ScreenCaptureListener(in);
                    }

                    @Override
                    public ScreenCaptureListener[] newArray(int size) {
                        return new ScreenCaptureListener[0];
                    }
                };
    }

    public abstract static class SynchronousScreenCaptureListener extends ScreenCaptureListener {
        SynchronousScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> consumer) {
            super(consumer);
        }

        @Nullable
        public abstract ScreenshotHardwareBuffer getBuffer();
    }

    public static SynchronousScreenCaptureListener createSyncCaptureListener() {
        throw new RuntimeException();
    }
}
