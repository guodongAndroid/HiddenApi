package android.os;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/13
 */
@RefineAs(PowerManager.class)
public class PowerManagerHidden {

    public static final int GO_TO_SLEEP_REASON_MIN = 0;
    public static final int GO_TO_SLEEP_REASON_APPLICATION = GO_TO_SLEEP_REASON_MIN;
    public static final int GO_TO_SLEEP_REASON_DEVICE_ADMIN = 1;
    public static final int GO_TO_SLEEP_REASON_TIMEOUT = 2;
    public static final int GO_TO_SLEEP_REASON_LID_SWITCH = 3;
    public static final int GO_TO_SLEEP_REASON_POWER_BUTTON = 4;
    public static final int GO_TO_SLEEP_REASON_HDMI = 5;
    public static final int GO_TO_SLEEP_REASON_SLEEP_BUTTON = 6;
    public static final int GO_TO_SLEEP_REASON_ACCESSIBILITY = 7;
    public static final int GO_TO_SLEEP_REASON_FORCE_SUSPEND = 8;
    public static final int GO_TO_SLEEP_REASON_INATTENTIVE = 9;
    public static final int GO_TO_SLEEP_REASON_QUIESCENT = 10;
    public static final int GO_TO_SLEEP_REASON_DISPLAY_GROUP_REMOVED = 11;
    public static final int GO_TO_SLEEP_REASON_DISPLAY_GROUPS_TURNED_OFF = 12;
    public static final int GO_TO_SLEEP_REASON_DEVICE_FOLD = 13;
    public static final int GO_TO_SLEEP_REASON_UNKNOWN = 14;

    @IntDef(value = {
            GO_TO_SLEEP_REASON_ACCESSIBILITY,
            GO_TO_SLEEP_REASON_APPLICATION,
            GO_TO_SLEEP_REASON_DEVICE_ADMIN,
            GO_TO_SLEEP_REASON_DEVICE_FOLD,
            GO_TO_SLEEP_REASON_DISPLAY_GROUP_REMOVED,
            GO_TO_SLEEP_REASON_DISPLAY_GROUPS_TURNED_OFF,
            GO_TO_SLEEP_REASON_FORCE_SUSPEND,
            GO_TO_SLEEP_REASON_HDMI,
            GO_TO_SLEEP_REASON_INATTENTIVE,
            GO_TO_SLEEP_REASON_LID_SWITCH,
            GO_TO_SLEEP_REASON_POWER_BUTTON,
            GO_TO_SLEEP_REASON_QUIESCENT,
            GO_TO_SLEEP_REASON_SLEEP_BUTTON,
            GO_TO_SLEEP_REASON_TIMEOUT,
            GO_TO_SLEEP_REASON_UNKNOWN,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface GoToSleepReason{}

    public static final int WAKE_REASON_UNKNOWN = 0;
    public static final int WAKE_REASON_POWER_BUTTON = 1;
    public static final int WAKE_REASON_APPLICATION = 2;
    public static final int WAKE_REASON_PLUGGED_IN = 3;
    public static final int WAKE_REASON_GESTURE = 4;
    public static final int WAKE_REASON_CAMERA_LAUNCH = 5;
    public static final int WAKE_REASON_WAKE_KEY = 6;
    public static final int WAKE_REASON_WAKE_MOTION = 7;
    public static final int WAKE_REASON_HDMI = 8;
    public static final int WAKE_REASON_LID = 9;
    public static final int WAKE_REASON_DISPLAY_GROUP_ADDED = 10;
    public static final int WAKE_REASON_DISPLAY_GROUP_TURNED_ON = 11;
    public static final int WAKE_REASON_UNFOLD_DEVICE = 12;
    public static final int WAKE_REASON_DREAM_FINISHED = 13;
    public static final int WAKE_REASON_TILT = 14;
    public static final int WAKE_REASON_TAP = 15;
    public static final int WAKE_REASON_LIFT = 16;
    public static final int WAKE_REASON_BIOMETRIC = 17;
    public static final int WAKE_REASON_DOCK = 18;

    @IntDef(value = {
            WAKE_REASON_UNKNOWN,
            WAKE_REASON_POWER_BUTTON,
            WAKE_REASON_APPLICATION,
            WAKE_REASON_PLUGGED_IN,
            WAKE_REASON_GESTURE,
            WAKE_REASON_CAMERA_LAUNCH,
            WAKE_REASON_WAKE_KEY,
            WAKE_REASON_WAKE_MOTION,
            WAKE_REASON_HDMI,
            WAKE_REASON_LID,
            WAKE_REASON_DISPLAY_GROUP_ADDED,
            WAKE_REASON_DISPLAY_GROUP_TURNED_ON,
            WAKE_REASON_UNFOLD_DEVICE,
            WAKE_REASON_DREAM_FINISHED,
            WAKE_REASON_TILT,
            WAKE_REASON_TAP,
            WAKE_REASON_LIFT,
            WAKE_REASON_BIOMETRIC,
            WAKE_REASON_DOCK,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WakeReason{}

    public void goToSleep(long time) {
        throw new UnsupportedOperationException();
    }

    public void goToSleep(long time, int reason, int flags) {
        throw new UnsupportedOperationException();
    }

    public void goToSleep(int displayId, long time, @GoToSleepReason int reason, int flags) {
        throw new UnsupportedOperationException();
    }

    public void wakeUp(long time) {
        throw new UnsupportedOperationException();
    }

    public void wakeUp(long time, String details) {
        throw new UnsupportedOperationException();
    }

    public void wakeUp(long time, @WakeReason int reason, String details) {
        throw new UnsupportedOperationException();
    }

    public void wakeUp(long time, @WakeReason int reason, String details, int displayId) {
        throw new UnsupportedOperationException();
    }
}
