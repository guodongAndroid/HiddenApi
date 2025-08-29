package rikka.hidden.compat;

import android.app.ActivityThread;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.RecoverySystem;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerHidden;
import android.util.Log;

import dev.rikka.tools.refine.Refine;

/**
 * <a href="https://cs.android.com/android/platform/superproject/+/android-latest-release:frameworks/base/services/core/java/com/android/server/MasterClearReceiver.java"/>
 * <p>
 * Created by guodongAndroid on 2025/8/29
 */
public class FactoryResetApis {

    private static final String TAG = "FactoryResetApis";

    /**
     * @noinspection deprecation
     */
    public static void factoryReset(boolean isWipeExternalStorage) {
        Context context = ActivityThread.currentApplication();
        Thread thread = new Thread("Factory Reset") {
            @Override
            public void run() {
                try {
                    RecoverySystem.rebootWipeUserData(context);
                } catch (Throwable e) {
                    Log.e(TAG, "Can't perform master clear/factory reset: " + e.getMessage(), e);
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isWipeExternalStorage) {
                StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
                WipeDataTask wipeDataTask = new WipeDataTask(storageManager, thread);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                    wipeDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    wipeDataTask.execute();
                }
            } else {
                thread.start();
            }
        } else {
            thread.start();
        }
    }

    /**
     * @noinspection deprecation
     */
    private static class WipeDataTask extends AsyncTask<Void, Void, Void> {
        private final Thread mChainedTask;
        private final StorageManagerHidden mStorageManager;

        private WipeDataTask(StorageManager storageManager, Thread chainedTask) {
            mStorageManager = Refine.unsafeCast(storageManager);
            mChainedTask = chainedTask;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.w(TAG, "Wiping adoptable disks");
            mStorageManager.wipeAdoptableDisks();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mChainedTask.start();
        }

    }
}
