package rikka.hidden.compat;

import static rikka.hidden.compat.Services.packageManager;

import android.Manifest;
import android.content.Context;
import android.content.ContextHidden;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSenderHidden;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageInstallerHidden;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerHidden;
import android.content.pm.VerificationParams;
import android.content.pm.VersionedPackage;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandleHidden;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import dev.rikka.tools.refine.Refine;
import rikka.hidden.compat.adapter.IntentSenderAdapter;
import rikka.hidden.compat.adapter.PackageInstallObserver2Adapter;
import rikka.hidden.compat.observer.IPackageDeleteObserver;
import rikka.hidden.compat.observer.IPackageInstallObserver;

/**
 * Created by guodongAndroid on 2025/8/8
 */
public class PackageInstallerApis {

    private static final String TAG = "PackageInstallerApis";

    public static void installPackage(@NonNull Context context, @NonNull String apkFilePath, @NonNull IPackageInstallObserver observer)
            throws RemoteException {
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            observer.onPackageInstalled("", false, -1, apkFilePath + " 文件不存在", null);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            installPackageApi28(context, observer, apkFile);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            installPackageApi24(context, observer, apkFile);
        } else {
            installPackageApiLegacy(context, observer, apkFile);
        }
    }

    public static void installPackageNoThrow(@NonNull Context context, @NonNull String apkFilePath, @NonNull IPackageInstallObserver observer) throws RemoteException {
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            observer.onPackageInstalled("", false, -1, apkFilePath + " 文件不存在", null);
            return;
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                installPackageApi28(context, observer, apkFile);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                installPackageApi24(context, observer, apkFile);
            } else {
                installPackageApiLegacy(context, observer, apkFile);
            }
        } catch (RemoteException e) {
            observer.onPackageInstalled("", false, -1, e.getMessage(), null);
        }
    }

    @RequiresPermission(anyOf = {Manifest.permission.REQUEST_DELETE_PACKAGES, Manifest.permission.DELETE_PACKAGES})
    public static void uninstallPackage(@NonNull Context context, @NonNull String packageName, @NonNull IPackageDeleteObserver observer) throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            uninstallPackageApi34(context, packageName, observer);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            uninstallPackageApi28(context, packageName, observer);
        } else {
            uninstallPackageApiLegacy(context, packageName, observer);
        }
    }

    public static void uninstallPackageNoThrow(@NonNull Context context, @NonNull String packageName, @NonNull IPackageDeleteObserver observer) throws RemoteException {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                uninstallPackageApi34(context, packageName, observer);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                uninstallPackageApi28(context, packageName, observer);
            } else {
                uninstallPackageApiLegacy(context, packageName, observer);
            }
        } catch (RemoteException e) {
            observer.onPackageDeleted(packageName, false, -1, e.getMessage(), null);
        }
    }

    private static void uninstallPackageApiLegacy(@NonNull Context context, @NonNull String packageName, @NonNull IPackageDeleteObserver observer) throws RemoteException {
        int userId = UserHandleHidden.getCallingUserId();
        packageManager.get().deletePackage(packageName, new IPackageDeleteObserver2.Stub() {
            @Override
            public void onPackageDeleted(String basePackageName, int returnCode, String msg) throws RemoteException {
                boolean isSuccessful = returnCode == PackageManagerHidden.DELETE_SUCCEEDED;
                observer.onPackageDeleted(packageName, isSuccessful, returnCode, msg, null);
            }
        }, userId, PackageManagerHidden.DELETE_ALL_USERS);
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private static void uninstallPackageApi28(@NonNull Context context, @NonNull String packageName, @NonNull IPackageDeleteObserver observer) {
        new Thread(() -> {
            PackageInstaller installer = context.getPackageManager().getPackageInstaller();
            IIntentSender senderAdapter = new IntentSenderAdapter() {
                @Override
                public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
                    int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE);
                    boolean isSuccessful = status == PackageInstaller.STATUS_SUCCESS;
                    String message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE);
                    Bundle extras = intent.getExtras();
                    observer.onPackageDeleted(packageName, isSuccessful, status, message, extras);
                }
            };

            IntentSenderHidden sender = getIntentSenderHidden(senderAdapter);
            installer.uninstall(packageName, Refine.unsafeCast(sender));
        }).start();
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private static void uninstallPackageApi34(@NonNull Context context, @NonNull String packageName, @NonNull IPackageDeleteObserver observer) {
        new Thread(() -> {
            PackageInstaller installer = context.getPackageManager().getPackageInstaller();
            IIntentSender senderAdapter = new IntentSenderAdapter() {
                @Override
                public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
                    int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE);
                    boolean isSuccessful = status == PackageInstaller.STATUS_SUCCESS;
                    String message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE);
                    Bundle extras = intent.getExtras();
                    observer.onPackageDeleted(packageName, isSuccessful, status, message, extras);
                }
            };

            IntentSenderHidden sender = getIntentSenderHidden(senderAdapter);
            installer.uninstall(new VersionedPackage(packageName, PackageManager.VERSION_CODE_HIGHEST), PackageManagerHidden.DELETE_ALL_USERS, Refine.unsafeCast(sender));
        }).start();
    }

    private static void installPackageApiLegacy(@NonNull Context context, @NonNull IPackageInstallObserver observer, File apkFile) throws RemoteException {
        Uri packageUri = Uri.fromFile(apkFile);
        int userId = UserHandleHidden.getCallingUserId();
        //noinspection InstantiationOfUtilityClass
        VerificationParams params = new VerificationParams(null, null, null, VerificationParams.NO_UID, null);
        packageManager.get().installPackageAsUser(packageUri.getPath(), new PackageInstallObserver2Adapter() {
            @Override
            public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) throws RemoteException {
                boolean isSuccessful = returnCode == PackageManagerHidden.INSTALL_SUCCEEDED;
                observer.onPackageInstalled(
                        basePackageName,
                        isSuccessful,
                        returnCode,
                        msg,
                        extras
                );
            }
        }, PackageManagerHidden.INSTALL_REPLACE_EXISTING | PackageManagerHidden.INSTALL_DONT_KILL_APP, context.getPackageName(), params, null, userId);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private static void installPackageApi24(@NonNull Context context, @NonNull IPackageInstallObserver observer, File apkFile) throws RemoteException {
        Uri packageUri = Uri.fromFile(apkFile);
        int userId = Refine.<ContextHidden>unsafeCast(context).getUserId();
        packageManager.get().installPackageAsUser(packageUri.getPath(), new PackageInstallObserver2Adapter() {
            @Override
            public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) throws RemoteException {
                boolean isSuccessful = returnCode == PackageManagerHidden.INSTALL_SUCCEEDED;
                observer.onPackageInstalled(
                        basePackageName,
                        isSuccessful,
                        returnCode,
                        msg,
                        extras
                );
            }
        }, PackageManagerHidden.INSTALL_REPLACE_EXISTING | PackageManagerHidden.INSTALL_DONT_KILL_APP, context.getPackageName(), userId);
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private static void installPackageApi28(@NonNull Context context, @NonNull IPackageInstallObserver observer, File apkFile) throws RemoteException {
        new Thread(() -> {
            PackageInstaller installer = context.getPackageManager().getPackageInstaller();
            PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                params.setDontKillApp(true);
            } else {
                PackageInstallerHidden.SessionParamsHidden paramsHidden = Refine.unsafeCast(params);
                paramsHidden.installFlags = paramsHidden.installFlags | PackageManagerHidden.INSTALL_DONT_KILL_APP;
            }

            try {
                int sessionId = installer.createSession(params);
                PackageInstaller.Session session = installer.openSession(sessionId);
                OutputStream os = session.openWrite("InstallApp.apk", 0, apkFile.length());
                FileInputStream fis = new FileInputStream(apkFile);
                byte[] buffer = new byte[8192];
                int read = fis.read(buffer);
                while (read != -1) {
                    os.write(buffer, 0, read);
                    read = fis.read(buffer);
                }
                session.fsync(os);
                fis.close();
                os.close();
                session.close();

                IIntentSender senderAdapter = new IntentSenderAdapter() {
                    @Override
                    public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) throws RemoteException {
                        String packageName = intent.getStringExtra(PackageInstaller.EXTRA_PACKAGE_NAME);
                        int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE);
                        boolean isSuccessful = status == PackageInstaller.STATUS_SUCCESS;
                        String message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE);
                        Bundle extras = intent.getExtras();

                        packageName = packageName == null ? "" : packageName;
                        observer.onPackageInstalled(packageName, isSuccessful, status, message, extras);
                    }
                };

                IntentSenderHidden sender = getIntentSenderHidden(senderAdapter);
                session.commit(Refine.unsafeCast(sender));
            } catch (IOException e) {
                Log.d(TAG, "installPackage: 创建或打开Session失败");
                observer.onPackageInstalled("", false, -1, e.getMessage(), null);
            }
        }).start();
    }

    @NonNull
    private static IntentSenderHidden getIntentSenderHidden(@NonNull IIntentSender sender) {
        return new IntentSenderHidden(sender);
    }
}
