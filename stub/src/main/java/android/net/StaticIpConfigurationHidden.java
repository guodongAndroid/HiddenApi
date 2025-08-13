package android.net;

import java.net.InetAddress;
import java.util.ArrayList;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/12
 */
@RefineAs(StaticIpConfiguration.class)
public class StaticIpConfigurationHidden {

    public LinkAddress ipAddress;
    public InetAddress gateway;
    public final ArrayList<InetAddress> dnsServers;
    public String domains;

    public StaticIpConfigurationHidden() {
        dnsServers = new ArrayList<>();
    }

    public LinkProperties toLinkProperties(String iface) {
        throw new UnsupportedOperationException();
    }
}
