package android.app;

import androidx.annotation.Nullable;

public class ActivityThread {

    public static ActivityThread systemMain() {
        throw new RuntimeException();
    }

    @Nullable
    public static ActivityThread currentActivityThread() {
        throw new RuntimeException();
    }

    @Nullable
    public static String currentPackageName() {
        throw new RuntimeException();
    }

    @Nullable
    public static String currentProcessName() {
        throw new RuntimeException();
    }

    @Nullable
    public static Application currentApplication() {
        throw new RuntimeException();
    }

    public Application getApplication() {
        throw new RuntimeException();
    }

    public ContextImpl getSystemContext() {
        throw new RuntimeException();
    }
}
