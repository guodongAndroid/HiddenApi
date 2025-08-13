package rikka.hidden.compat;

import static rikka.hidden.compat.Services.packageManager;

import android.Manifest;
import android.app.ActivityThread;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSenderHidden;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageInstaller;
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

    public static void installPackage(@NonNull String apkFilePath, @NonNull IPackageInstallObserver observer)
            throws RemoteException {
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            observer.onPackageInstalled("", false, -1, apkFilePath + " 文件不存在", null);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            installPackageApi28(observer, apkFile);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            installPackageApi24(observer, apkFile);
        } else {
            installPackageApiLegacy(observer, apkFile);
        }
    }

    public static void installPackageNoThrow(@NonNull String apkFilePath, @NonNull IPackageInstallObserver observer) {
        try {
            installPackage(apkFilePath, observer);
        } catch (RemoteException ignore) {
        }
    }

    @RequiresPermission(anyOf = {Manifest.permission.REQUEST_DELETE_PACKAGES, Manifest.permission.DELETE_PACKAGES})
    public static void uninstallPackage(@NonNull String packageName, @NonNull IPackageDeleteObserver observer) throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            uninstallPackageApi34(packageName, observer);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            uninstallPackageApi28(packageName, observer);
        } else {
            uninstallPackageApiLegacy(packageName, observer);
        }
    }

    public static void uninstallPackageNoThrow(@NonNull String packageName, @NonNull IPackageDeleteObserver observer) {
        try {
            uninstallPackage(packageName, observer);
        } catch (RemoteException ignore) {
        }
    }

    private static void uninstallPackageApiLegacy(@NonNull String packageName, @NonNull IPackageDeleteObserver observer) throws RemoteException {
        int userId = UserHandleHidden.myUserId();
        packageManager.get().deletePackage(packageName, new IPackageDeleteObserver2.Stub() {
            @Override
            public void onPackageDeleted(String basePackageName, int returnCode, String msg) {
                boolean isSuccessful = returnCode == PackageManagerHidden.DELETE_SUCCEEDED;
                observer.onPackageDeleted(packageName, isSuccessful, returnCode, msg, null);
            }
        }, userId, PackageManagerHidden.DELETE_ALL_USERS);
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private static void uninstallPackageApi28(@NonNull String packageName, @NonNull IPackageDeleteObserver observer) throws RemoteException {
        IPackageInstaller installer = packageManager.get().getPackageInstaller();

        //noinspection ExtractMethodRecommender
        IIntentSender senderAdapter = new IntentSenderAdapter() {
            @Override
            public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) {
                int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE);
                boolean isSuccessful = status == PackageInstaller.STATUS_SUCCESS;
                String message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE);
                Bundle extras = intent.getExtras();
                observer.onPackageDeleted(packageName, isSuccessful, status, message, extras);
            }
        };

        IntentSenderHidden sender = new IntentSenderHidden(senderAdapter);
        String callerPackageName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            callerPackageName = ActivityThread.systemMain().getSystemContext().getOpPackageName();
        } else {
            callerPackageName = ActivityThread.systemMain().getSystemContext().getPackageName();
        }
        installer.uninstall(new VersionedPackage(packageName, PackageManager.VERSION_CODE_HIGHEST), callerPackageName, 0, Refine.unsafeCast(sender), UserHandleHidden.myUserId());
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private static void uninstallPackageApi34(@NonNull String packageName, @NonNull IPackageDeleteObserver observer) throws RemoteException {
        IPackageInstaller installer = packageManager.get().getPackageInstaller();
        IIntentSender senderAdapter = new IntentSenderAdapter() {
            @Override
            public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) {
                int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE);
                boolean isSuccessful = status == PackageInstaller.STATUS_SUCCESS;
                String message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE);
                Bundle extras = intent.getExtras();
                observer.onPackageDeleted(packageName, isSuccessful, status, message, extras);
            }
        };

        IntentSenderHidden sender = new IntentSenderHidden(senderAdapter);
        installer.uninstall(new VersionedPackage(packageName, PackageManager.VERSION_CODE_HIGHEST), ActivityThread.systemMain().getSystemContext().getOpPackageName(), 0, Refine.unsafeCast(sender), UserHandleHidden.myUserId());
    }

    private static void installPackageApiLegacy(@NonNull IPackageInstallObserver observer, File apkFile) throws RemoteException {
        Uri packageUri = Uri.fromFile(apkFile);
        int userId = UserHandleHidden.getCallingUserId();
        //noinspection InstantiationOfUtilityClass
        VerificationParams params = new VerificationParams(null, null, null, VerificationParams.NO_UID, null);
        packageManager.get().installPackageAsUser(packageUri.getPath(), new PackageInstallObserver2Adapter() {
            @Override
            public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) {
                boolean isSuccessful = returnCode == PackageManagerHidden.INSTALL_SUCCEEDED;
                observer.onPackageInstalled(
                        basePackageName,
                        isSuccessful,
                        returnCode,
                        msg,
                        extras
                );
            }
        }, PackageManagerHidden.INSTALL_REPLACE_EXISTING | PackageManagerHidden.INSTALL_DONT_KILL_APP, ActivityThread.systemMain().getSystemContext().getPackageName(), params, null, userId);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private static void installPackageApi24(@NonNull IPackageInstallObserver observer, File apkFile) throws RemoteException {
        Uri packageUri = Uri.fromFile(apkFile);
        packageManager.get().installPackageAsUser(packageUri.getPath(), new PackageInstallObserver2Adapter() {
            @Override
            public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) {
                boolean isSuccessful = returnCode == PackageManagerHidden.INSTALL_SUCCEEDED;
                observer.onPackageInstalled(
                        basePackageName,
                        isSuccessful,
                        returnCode,
                        msg,
                        extras
                );
            }
        }, PackageManagerHidden.INSTALL_REPLACE_EXISTING | PackageManagerHidden.INSTALL_DONT_KILL_APP, ActivityThread.systemMain().getSystemContext().getPackageName(), UserHandleHidden.myUserId());
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private static void installPackageApi28(@NonNull IPackageInstallObserver observer, File apkFile) {
        new Thread(() -> {
            PackageInstaller installer = ActivityThread.systemMain().getSystemContext().getPackageManager().getPackageInstaller();
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

                //noinspection ExtractMethodRecommender
                IIntentSender senderAdapter = new IntentSenderAdapter() {
                    @Override
                    public void send(int code, Intent intent, String resolvedType, IBinder whitelistToken, IIntentReceiver finishedReceiver, String requiredPermission, Bundle options) {
                        String packageName = intent.getStringExtra(PackageInstaller.EXTRA_PACKAGE_NAME);
                        int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE);
                        boolean isSuccessful = status == PackageInstaller.STATUS_SUCCESS;
                        String message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE);
                        Bundle extras = intent.getExtras();

                        packageName = packageName == null ? "" : packageName;
                        observer.onPackageInstalled(packageName, isSuccessful, status, message, extras);
                    }
                };

                IntentSenderHidden sender = new IntentSenderHidden(senderAdapter);
                session.commit(Refine.unsafeCast(sender));
            } catch (Exception e) {
                Log.d(TAG, "installPackage: 创建或打开Session失败");
                observer.onPackageInstalled("", false, -1, e.getMessage(), null);
            }
        }).start();
    }
}
