package rikka.hidden.compat.domain;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by guodongAndroid on 2025/8/12
 */
public class NetworkAddress {

    public static final NetworkAddress UNASSIGNED = new NetworkAddress(
            IpAssignment.UNASSIGNED,
            "0.0.0.0",
            "0.0.0.0",
            "0.0.0.0",
            "0.0.0.0",
            "0.0.0.0");

    @IpAssignment
    private final int ipAssignment;
    private final String address;
    private final String netmask;
    private final String gateway;
    private final String dns1;
    private final String dns2;

    public NetworkAddress(@IpAssignment int ipAssignment, @NonNull String address, @NonNull String netmask, @NonNull String gateway, @NonNull String dns1, @Nullable String dns2) {
        this.ipAssignment = ipAssignment;
        this.address = address;
        this.gateway = gateway;
        this.netmask = netmask;
        this.dns1 = dns1;
        this.dns2 = dns2;
    }

    public int getIpAssignment() {
        return ipAssignment;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public String getNetmask() {
        return netmask;
    }

    @NonNull
    public String getGateway() {
        return gateway;
    }

    @NonNull
    public String getDns1() {
        return dns1;
    }

    @Nullable
    public String getDns2() {
        return dns2;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({IpAssignment.STATIC, IpAssignment.DHCP, IpAssignment.UNASSIGNED})
    public @interface IpAssignment {
        int STATIC = 1;
        int DHCP = 2;
        int UNASSIGNED = 3;
    }
}
