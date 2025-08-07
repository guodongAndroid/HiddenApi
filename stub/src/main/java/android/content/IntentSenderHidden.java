package android.content;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/7
 */
@RefineAs(IntentSender.class)
public class IntentSenderHidden {

    public IntentSenderHidden(IIntentSender target) {
        throw new RuntimeException();
    }
}
