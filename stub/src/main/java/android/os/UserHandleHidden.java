package android.os;

import androidx.annotation.RequiresApi;

import dev.rikka.tools.refine.RefineAs;

@RefineAs(UserHandle.class)
public class UserHandleHidden {
    public static UserHandle ALL;

    @RequiresApi(Build.VERSION_CODES.N)
    public static UserHandle of(int userId) {
        throw new RuntimeException();
    }

    /**
     * @see #of(int)
     */
    public UserHandleHidden(int h) {
        throw new RuntimeException();
    }

    public int getIdentifier() {
        throw new RuntimeException();
    }

    public static int getUserId(int uid) {
        throw new RuntimeException();
    }

    public static int myUserId() {
        throw new RuntimeException();
    }

    public static int getCallingUserId() {
        throw new RuntimeException();
    }
}
