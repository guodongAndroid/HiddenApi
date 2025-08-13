package android.net;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/13
 */
@RefineAs(RouteInfo.class)
public class RouteInfoHidden {

    public boolean isIPv4Default() {
        throw new UnsupportedOperationException();
    }

    // Android10不再是隐藏方法
    public boolean hasGateway() {
        throw new UnsupportedOperationException();
    }
}
