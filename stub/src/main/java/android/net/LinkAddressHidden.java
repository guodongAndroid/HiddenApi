package android.net;

import java.net.InetAddress;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/12
 */
@RefineAs(LinkAddress.class)
public class LinkAddressHidden {
    private InetAddress address;
    private int prefixLength;

    public LinkAddressHidden(InetAddress address, int prefixLength) {
        throw new UnsupportedOperationException();
    }

    public InetAddress getAddress() {
        throw new UnsupportedOperationException();
    }

    public int getPrefixLength() {
        throw new UnsupportedOperationException();
    }

    public int getNetworkPrefixLength() {
        throw new UnsupportedOperationException();
    }
}
