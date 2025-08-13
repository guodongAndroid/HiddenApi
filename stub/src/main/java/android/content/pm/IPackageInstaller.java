package android.content.pm;

import android.content.IntentSender;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import androidx.annotation.RequiresApi;

public interface IPackageInstaller extends IInterface {

    int createSession(PackageInstaller.SessionParams params, String installerPackageName, int userId)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.S)
    int createSession(PackageInstaller.SessionParams params, String installerPackageName,
                      String installerAttributionTag, int userId) throws RemoteException;

    void abandonSession(int sessionId)
            throws RemoteException;

    IPackageInstallerSession openSession(int sessionId)
            throws RemoteException;

    ParceledListSlice<PackageInstaller.SessionInfo> getMySessions(String installerPackageName, int userId)
            throws RemoteException;

    void uninstall(String packageName, int flags, IntentSender statusReceiver, int userId)
            throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.M)
    void uninstall(String packageName, String callerPackageName, int flags,
                   IntentSender statusReceiver, int userId) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.O)
    void uninstall(VersionedPackage versionedPackage, String callerPackageName, int flags,
                   IntentSender statusReceiver, int userId) throws RemoteException;

    @RequiresApi(Build.VERSION_CODES.R)
    void uninstallExistingPackage(VersionedPackage versionedPackage, String callerPackageName,
                                  IntentSender statusReceiver, int userId) throws RemoteException;

    abstract class Stub extends Binder implements IPackageInstaller {

        public static IPackageInstaller asInterface(IBinder binder) {
            throw new UnsupportedOperationException();
        }
    }
}
