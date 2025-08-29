package android.os.storage;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/29
 */
@RefineAs(StorageManager.class)
public class StorageManagerHidden {

    public void wipeAdoptableDisks() {
        throw new UnsupportedOperationException();
    }
}
