package rikka.hidden.compat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayHidden;
import android.view.Surface;
import android.view.SurfaceControlHidden;
import android.view.WindowManager;
import android.window.ScreenCapture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import dev.rikka.tools.refine.Refine;

/**
 * Created by guodongAndroid on 2025/8/7
 */
public class TakeScreenshotApis {

    @Nullable
    public static Bitmap takeScreenshot(@NonNull Context context) throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return takeScreenshotApi34(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return takeScreenshotApi31(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return takeScreenshotApi28(context);
        } else {
            return takeScreenshotLegacy(context);
        }
    }

    @Nullable
    public static Bitmap takeScreenshotNoThrow(@NonNull Context context) {
        try {
            return takeScreenshot(context);
        } catch (Throwable e) {
            return null;
        }
    }

    @Nullable
    private static Bitmap takeScreenshotLegacy(@NonNull Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        if (display == null) {
            return null;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        Matrix matrix = new Matrix();

        float[] dims = new float[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
        int degrees = getDegreesForRotation(display.getRotation());
        boolean requiresRotation = degrees > 0;
        if (requiresRotation) {
            matrix.reset();
            matrix.preRotate(-degrees);
            matrix.mapPoints(dims);
            dims[0] = Math.abs(dims[0]);
            dims[1] = Math.abs(dims[1]);
        }

        Bitmap bitmap = SurfaceControlHidden.screenshot((int) dims[0], (int) dims[1]);
        if (bitmap == null) {
            return null;
        }

        if (requiresRotation) {
            Bitmap ss = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ss);
            canvas.translate(((float) ss.getWidth() / 2), ((float) ss.getHeight() / 2));
            canvas.rotate(degrees);
            canvas.translate(-dims[0] / 2, -dims[1] / 2);
            canvas.drawBitmap(bitmap, 0F, 0F, null);
            canvas.setBitmap(null);
            bitmap.recycle();
            bitmap = ss;
        }

        bitmap.setHasAlpha(false);
        return bitmap;
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Nullable
    private static Bitmap takeScreenshotApi28(@NonNull Context context) {
        WindowManager manager = context.getSystemService(WindowManager.class);
        Display display = manager.getDefaultDisplay();
        if (display == null) {
            return null;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        Bitmap bitmap = SurfaceControlHidden.screenshot(rect, rect.width(), rect.height(), display.getRotation());
        if (bitmap == null) {
            return null;
        }

        bitmap.setHasAlpha(false);
        return bitmap;
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Nullable
    private static Bitmap takeScreenshotApi31(@NonNull Context context) {
        DisplayManager manager = context.getSystemService(DisplayManager.class);
        Display display = manager.getDisplay(Display.DEFAULT_DISPLAY);
        if (display == null) {
            return null;
        }

        DisplayAddress address = Refine.<DisplayHidden>unsafeCast(display).getAddress();
        if (address == null) {
            return null;
        }

        if (!(address instanceof DisplayAddress.Physical)) {
            return null;
        }

        IBinder displayToken = SurfaceControlHidden.getPhysicalDisplayToken(((DisplayAddress.Physical) address).getPhysicalDisplayId());
        if (displayToken == null) {
            return null;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        SurfaceControlHidden.DisplayCaptureArgs.Builder builder = new SurfaceControlHidden.DisplayCaptureArgs.Builder(displayToken);
        SurfaceControlHidden.DisplayCaptureArgs captureArgs = builder.setSourceCrop(rect).setSize(rect.width(), rect.height()).build();

        SurfaceControlHidden.ScreenshotHardwareBuffer screenshotBuffer = SurfaceControlHidden.captureDisplay(captureArgs);
        if (screenshotBuffer == null) {
            return null;
        }

        Bitmap bitmap = screenshotBuffer.asBitmap();
        if (bitmap == null) {
            return null;
        }

        bitmap.setHasAlpha(false);
        return bitmap;
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Nullable
    private static Bitmap takeScreenshotApi34(@NonNull Context context) throws RemoteException {
        DisplayManager manager = context.getSystemService(DisplayManager.class);
        Display display = manager.getDisplay(Display.DEFAULT_DISPLAY);
        if (display == null) {
            return null;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        //noinspection rawtypes
        ScreenCapture.CaptureArgs captureArgs = new ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build();
        ScreenCapture.SynchronousScreenCaptureListener syncCaptureListener = ScreenCapture.createSyncCaptureListener();

        Services.windowManager.get().captureDisplay(display.getDisplayId(), captureArgs, syncCaptureListener);
        ScreenCapture.ScreenshotHardwareBuffer buffer = syncCaptureListener.getBuffer();
        if (buffer == null) {
            return null;
        }

        Bitmap bitmap = buffer.asBitmap();
        if (bitmap == null) {
            return null;
        }

        bitmap.setHasAlpha(false);
        return bitmap;
    }

    private static int getDegreesForRotation(int rotation) {
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_90:
                degrees = 360 - 90;
                break;
            case Surface.ROTATION_180:
                degrees = 360 - 180;
                break;
            case Surface.ROTATION_270:
                degrees = 360 - 270;
                break;
            default:
                break;
        }
        return degrees;
    }
}
