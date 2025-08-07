package rikka.hidden.compat.observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by guodongAndroid on 2025/8/8
 */
public interface IPackageInstallObserver {
    void onPackageInstalled(@NonNull String packageName, boolean isSuccessful, int status, @Nullable String message, @Nullable Bundle extras);
}