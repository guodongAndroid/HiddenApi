package android.content.pm;

import android.net.Uri;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/8
 */
@RefineAs(VerificationParams.class)
public class VerificationParams {
    public static final int NO_UID = -1;

    public VerificationParams(Uri verificationURI, Uri originatingURI, Uri referrer,
                              int originatingUid, ManifestDigest manifestDigest) {
        throw new RuntimeException();
    }
}
