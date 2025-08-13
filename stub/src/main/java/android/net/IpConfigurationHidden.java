package android.net;

import dev.rikka.tools.refine.RefineAs;

/**
 * Created by guodongAndroid on 2025/8/12
 */
@RefineAs(IpConfiguration.class)
public class IpConfigurationHidden {

    public enum IpAssignment {
        STATIC,
        DHCP,
        UNASSIGNED
    }

    public enum ProxySettings {
        NONE,
        STATIC,
        UNASSIGNED,
        PAC
    }

    public IpAssignment ipAssignment;
    public StaticIpConfigurationHidden staticIpConfiguration;
    public ProxySettings proxySettings;
    public ProxyInfo httpProxy;

    public IpConfigurationHidden(IpAssignment ipAssignment,
                                 ProxySettings proxySettings,
                                 StaticIpConfigurationHidden staticIpConfiguration,
                                 ProxyInfo httpProxy) {
    }
}
