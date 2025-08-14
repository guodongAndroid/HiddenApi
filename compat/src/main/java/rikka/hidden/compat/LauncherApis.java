package rikka.hidden.compat;

import static rikka.hidden.compat.Services.activityManager;
import static rikka.hidden.compat.Services.packageManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextHidden;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import dev.rikka.tools.refine.Refine;

/**
 * Created by guodongAndroid on 2025/8/8
 */
public class LauncherApis {

    private static final String TAG = "LauncherApis";

    @Nullable
    public static ComponentName getLauncher() throws RemoteException {
        List<ResolveInfo> resolveInfos = new ArrayList<>();
        return packageManager.get().getHomeActivities(resolveInfos);
    }

    @Nullable
    public static ComponentName getLauncherNoThrow() {
        List<ResolveInfo> resolveInfos = new ArrayList<>();
        try {
            return packageManager.get().getHomeActivities(resolveInfos);
        } catch (RemoteException e) {
            return null;
        }
    }

    public static boolean setLauncher(@NonNull Context context, @NonNull String packageName)
            throws RemoteException, PackageManager.NameNotFoundException {
        ComponentName launchComponentName = getAppLaunchComponentName(context, packageName);
        if (launchComponentName == null) {
            String appName = getAppName(context, packageName);
            Log.d(
                    TAG, "setLauncher: App(" + appName + ")缺少以下两个属性\n" +
                            "<category android:name=\"android.intent.category.HOME\"/>\n" +
                            "<category android:name=\"android.intent.category.DEFAULT\"/>"
            );
            return false;
        }

        ComponentName homeComponentName = getLauncher();
        if (homeComponentName != null && homeComponentName == launchComponentName) {
            return true;
        }

        IPackageManager manager = packageManager.get();
        int userId = Refine.<ContextHidden>unsafeCast(context).getUserId();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            manager.setHomeActivity(launchComponentName, userId);
        } else {
            IntentFilter homeFilter = new IntentFilter(Intent.ACTION_MAIN);
            homeFilter.addCategory(Intent.CATEGORY_HOME);
            homeFilter.addCategory(Intent.CATEGORY_DEFAULT);
            homeFilter.addCategory(Intent.CATEGORY_LAUNCHER);

            manager.replacePreferredActivity(
                    homeFilter,
                    IntentFilter.MATCH_CATEGORY_EMPTY,
                    new ComponentName[]{},
                    launchComponentName,
                    userId);
        }

        if (homeComponentName != null) {
            activityManager.get().killBackgroundProcesses(homeComponentName.getPackageName(), userId);
        }

        return true;
    }

    public static boolean setLauncherNoThrow(@NonNull Context context, @NonNull String packageName) {
        try {
            return setLauncher(context, packageName);
        } catch (RemoteException | PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Nullable
    private static ComponentName getAppLaunchComponentName(@NonNull Context context, @NonNull String packageName) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(homeIntent, 0);
        if (resolveInfos.isEmpty()) {
            return null;
        }

        for (ResolveInfo resolveInfo : resolveInfos) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (TextUtils.equals(activityInfo.packageName, packageName)) {
                return new ComponentName(activityInfo.packageName, activityInfo.name);
            }
        }

        return null;
    }

    private static String getAppName(@NonNull Context context, @NonNull String packageName) throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        ApplicationInfo applicationInfo = manager.getApplicationInfo(packageName, 0);
        return manager.getApplicationLabel(applicationInfo).toString();
    }
}
