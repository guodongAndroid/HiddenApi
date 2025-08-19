package rikka.hidden.compat;

import static rikka.hidden.compat.Services.connectivityManager;
import static rikka.hidden.compat.Services.ethernetManager;
import static rikka.hidden.compat.Services.networkManagementService;

import android.app.ActivityThread;
import android.content.Context;
import android.net.EthernetNetworkUpdateRequest;
import android.net.IConnectivityManager;
import android.net.IEthernetManager;
import android.net.IEthernetServiceListener;
import android.net.INetworkInterfaceOutcomeReceiver;
import android.net.InterfaceConfiguration;
import android.net.IpConfiguration;
import android.net.IpConfigurationHidden;
import android.net.LinkAddress;
import android.net.LinkAddressHidden;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkUtils;
import android.net.RouteInfo;
import android.net.RouteInfoHidden;
import android.net.StaticIpConfiguration;
import android.net.StaticIpConfigurationHidden;
import android.os.Build;
import android.os.RemoteException;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import dev.rikka.tools.refine.Refine;
import rikka.hidden.compat.domain.NetworkAddress;

/**
 * Created by guodongAndroid on 2025/8/12
 */
public class EthernetManagerApis {

    public static final String ETH0_INTERFACE_NAME = "eth0";

    public static String[] getAvailableInterfaces() throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return ethernetManager.get().getAvailableInterfaces();
        }

        return new String[]{ETH0_INTERFACE_NAME};
    }

    public static String[] getAvailableInterfacesNoThrow() {
        try {
            return getAvailableInterfaces();
        } catch (RemoteException e) {
            return new String[]{};
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static boolean isAvailable(@Nullable String iface) throws RemoteException {
        IEthernetManager manager = ethernetManager.get();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return manager.isAvailable(iface);
        }

        return manager.isAvailable();
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static boolean isAvailableNoThrow(@Nullable String iface) {
        try {
            return isAvailable(iface);
        } catch (RemoteException e) {
            return false;
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void addListener(IEthernetServiceListener listener) throws RemoteException {
        ethernetManager.get().addListener(listener);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void addListenerNoThrow(IEthernetServiceListener listener) {
        try {
            ethernetManager.get().addListener(listener);
        } catch (Throwable ignore) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void removeListener(IEthernetServiceListener listener) throws RemoteException {
        ethernetManager.get().removeListener(listener);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void removeListenerNoThrow(IEthernetServiceListener listener) {
        try {
            ethernetManager.get().removeListener(listener);
        } catch (Throwable ignore) {
        }
    }

    public static void setStaticAddress(@NonNull String ipAddress, @NonNull String netmask, @NonNull String gateway, @NonNull String dns1, @Nullable String dns2) throws RemoteException {
        InetAddress inetAddress = NetworkUtils.numericToInetAddress(ipAddress);
        int prefixLength = NetworkUtils.netmaskToPrefixLength((Inet4Address) NetworkUtils.numericToInetAddress(netmask));
        LinkAddressHidden linkAddress = new LinkAddressHidden(inetAddress, prefixLength);

        List<InetAddress> dnsServers = new ArrayList<>();
        dnsServers.add(NetworkUtils.numericToInetAddress(dns1));
        if (dns2 != null) {
            dnsServers.add(NetworkUtils.numericToInetAddress(dns2));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            StaticIpConfiguration staticIpConfiguration = new StaticIpConfiguration.Builder()
                    .setIpAddress(Refine.unsafeCast(linkAddress))
                    .setGateway(NetworkUtils.numericToInetAddress(gateway))
                    .setDnsServers(dnsServers)
                    .build();

            IpConfiguration ipConfiguration = new IpConfiguration.Builder()
                    .setStaticIpConfiguration(staticIpConfiguration)
                    .setHttpProxy(null)
                    .build();

            ethernetManager.get().setConfiguration(ETH0_INTERFACE_NAME, ipConfiguration);
        } else {
            StaticIpConfigurationHidden staticIpConfiguration = new StaticIpConfigurationHidden();
            staticIpConfiguration.ipAddress = Refine.unsafeCast(linkAddress);
            staticIpConfiguration.gateway = NetworkUtils.numericToInetAddress(gateway);
            staticIpConfiguration.dnsServers.addAll(dnsServers);

            IpConfigurationHidden ipConfigurationHidden = new IpConfigurationHidden(
                    IpConfigurationHidden.IpAssignment.STATIC,
                    IpConfigurationHidden.ProxySettings.NONE,
                    Refine.unsafeCast(staticIpConfiguration),
                    null);

            IpConfiguration ipConfiguration = Refine.unsafeCast(ipConfigurationHidden);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ethernetManager.get().setConfiguration(ETH0_INTERFACE_NAME, Refine.unsafeCast(ipConfiguration));
            } else {
                ethernetManager.get().setConfiguration(Refine.unsafeCast(ipConfiguration));
            }
        }
    }

    public static void setStaticAddressNoThrow(@NonNull String ipAddress, @NonNull String netmask, @NonNull String gateway, @NonNull String dns1, @Nullable String dns2) {
        try {
            setStaticAddress(ipAddress, netmask, gateway, dns1, dns2);
        } catch (Throwable ignore) {
        }
    }

    public static void setStaticAddress(@NonNull String iface, @NonNull String ipAddress, @NonNull String netmask, @NonNull String gateway, @NonNull String dns1, @Nullable String dns2) throws RemoteException {
        InetAddress inetAddress = NetworkUtils.numericToInetAddress(ipAddress);
        int prefixLength = NetworkUtils.netmaskToPrefixLength((Inet4Address) NetworkUtils.numericToInetAddress(netmask));
        LinkAddressHidden linkAddress = new LinkAddressHidden(inetAddress, prefixLength);

        List<InetAddress> dnsServers = new ArrayList<>();
        dnsServers.add(NetworkUtils.numericToInetAddress(dns1));
        if (dns2 != null) {
            dnsServers.add(NetworkUtils.numericToInetAddress(dns2));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            StaticIpConfiguration staticIpConfiguration = new StaticIpConfiguration.Builder()
                    .setIpAddress(Refine.unsafeCast(linkAddress))
                    .setGateway(NetworkUtils.numericToInetAddress(gateway))
                    .setDnsServers(dnsServers)
                    .build();

            IpConfiguration ipConfiguration = new IpConfiguration.Builder()
                    .setStaticIpConfiguration(staticIpConfiguration)
                    .setHttpProxy(null)
                    .build();

            ethernetManager.get().setConfiguration(iface, ipConfiguration);
        } else {
            StaticIpConfigurationHidden staticIpConfiguration = new StaticIpConfigurationHidden();
            staticIpConfiguration.ipAddress = Refine.unsafeCast(linkAddress);
            staticIpConfiguration.gateway = NetworkUtils.numericToInetAddress(gateway);
            staticIpConfiguration.dnsServers.addAll(dnsServers);

            IpConfigurationHidden ipConfigurationHidden = new IpConfigurationHidden(
                    IpConfigurationHidden.IpAssignment.STATIC,
                    IpConfigurationHidden.ProxySettings.NONE,
                    Refine.unsafeCast(staticIpConfiguration),
                    null);

            IpConfiguration ipConfiguration = Refine.unsafeCast(ipConfigurationHidden);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ethernetManager.get().setConfiguration(iface, Refine.unsafeCast(ipConfiguration));
            } else {
                ethernetManager.get().setConfiguration(Refine.unsafeCast(ipConfiguration));
            }
        }
    }

    public static void setStaticAddressNoThrow(@NonNull String iface, @NonNull String ipAddress, @NonNull String netmask, @NonNull String gateway, @NonNull String dns1, @Nullable String dns2) {
        try {
            setStaticAddress(iface, ipAddress, netmask, gateway, dns1, dns2);
        } catch (Throwable ignore) {
        }
    }

    public static void setDhcpAddress() throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            IpConfiguration ipConfiguration = new IpConfiguration.Builder()
                    .setStaticIpConfiguration(null)
                    .setHttpProxy(null)
                    .build();

            ethernetManager.get().setConfiguration(ETH0_INTERFACE_NAME, ipConfiguration);
        } else {
            IpConfigurationHidden ipConfigurationHidden = new IpConfigurationHidden(
                    IpConfigurationHidden.IpAssignment.DHCP,
                    IpConfigurationHidden.ProxySettings.NONE,
                    null,
                    null);

            IpConfiguration ipConfiguration = Refine.unsafeCast(ipConfigurationHidden);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ethernetManager.get().setConfiguration(ETH0_INTERFACE_NAME, Refine.unsafeCast(ipConfiguration));
            } else {
                ethernetManager.get().setConfiguration(Refine.unsafeCast(ipConfiguration));
            }
        }
    }

    public static void setDhcpAddressNoThrow() {
        try {
            setDhcpAddress();
        } catch (Throwable ignore) {
        }
    }

    public static void setDhcpAddress(@NonNull String iface) throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            IpConfiguration ipConfiguration = new IpConfiguration.Builder()
                    .setStaticIpConfiguration(null)
                    .setHttpProxy(null)
                    .build();

            ethernetManager.get().setConfiguration(iface, ipConfiguration);
        } else {
            IpConfigurationHidden ipConfigurationHidden = new IpConfigurationHidden(
                    IpConfigurationHidden.IpAssignment.DHCP,
                    IpConfigurationHidden.ProxySettings.NONE,
                    null,
                    null);

            IpConfiguration ipConfiguration = Refine.unsafeCast(ipConfigurationHidden);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ethernetManager.get().setConfiguration(iface, Refine.unsafeCast(ipConfiguration));
            } else {
                ethernetManager.get().setConfiguration(Refine.unsafeCast(ipConfiguration));
            }
        }
    }

    public static void setDhcpAddressNoThrow(@NonNull String iface) {
        try {
            setDhcpAddress(iface);
        } catch (Throwable ignore) {
        }
    }

    public static NetworkAddress getNetworkAddress() throws RemoteException {
        IEthernetManager manager = ethernetManager.get();
        IpConfiguration configuration;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            configuration = manager.getConfiguration(ETH0_INTERFACE_NAME);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            configuration = manager.getConfiguration(ETH0_INTERFACE_NAME);
        } else {
            configuration = manager.getConfiguration();
        }

        if (configuration == null) {
            return NetworkAddress.UNASSIGNED;
        }

        IpConfigurationHidden configurationHidden = Refine.unsafeCast(configuration);
        IpConfigurationHidden.IpAssignment ipAssignment = configurationHidden.ipAssignment;
        if (ipAssignment == IpConfigurationHidden.IpAssignment.STATIC) {
            StaticIpConfigurationHidden staticIpConfiguration = configurationHidden.staticIpConfiguration;
            if (staticIpConfiguration == null) {
                return NetworkAddress.UNASSIGNED;
            }

            return getStaticNetworkAddress(staticIpConfiguration);
        }

        return getDhcpNetworkAddress(ETH0_INTERFACE_NAME);
    }

    public static NetworkAddress getNetworkAddressNoThrow() {
        try {
            return getNetworkAddress();
        } catch (RemoteException e) {
            return NetworkAddress.UNASSIGNED;
        }
    }

    public static NetworkAddress getNetworkAddress(@NonNull String iface) throws RemoteException {
        IEthernetManager manager = ethernetManager.get();
        IpConfiguration configuration;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            configuration = manager.getConfiguration(iface);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            configuration = manager.getConfiguration(iface);
        } else {
            configuration = manager.getConfiguration();
        }

        if (configuration == null) {
            return NetworkAddress.UNASSIGNED;
        }

        IpConfigurationHidden configurationHidden = Refine.unsafeCast(configuration);
        IpConfigurationHidden.IpAssignment ipAssignment = configurationHidden.ipAssignment;
        if (ipAssignment == IpConfigurationHidden.IpAssignment.STATIC) {
            StaticIpConfigurationHidden staticIpConfiguration = configurationHidden.staticIpConfiguration;
            if (staticIpConfiguration == null) {
                return NetworkAddress.UNASSIGNED;
            }

            return getStaticNetworkAddress(staticIpConfiguration);
        }

        return getDhcpNetworkAddress(iface);
    }

    public static NetworkAddress getNetworkAddressNoThrow(@NonNull String iface) {
        try {
            return getNetworkAddress(iface);
        } catch (RemoteException e) {
            return NetworkAddress.UNASSIGNED;
        }
    }

    @Nullable
    public static String getMacAddress() {
        return getMacAddress(ETH0_INTERFACE_NAME);
    }

    @Nullable
    public static String getMacAddress(@NonNull String iface) {
        try {
            InterfaceConfiguration ifcg = networkManagementService.get().getInterfaceConfig(iface);
            String address = ifcg.getHardwareAddress();
            if (!TextUtils.isEmpty(address)) {
                return address;
            }
        } catch (Throwable ignore) {
        }

        File file = new File("/sys/class/net/" + iface + "/address");
        if (!file.exists()) {
            return null;
        }

        InputStream is;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                is = Files.newInputStream(file.toPath());
            } else {
                //noinspection IOStreamConstructor
                is = new FileInputStream(file);
            }
        } catch (IOException e) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public static void setEthernetEnabled(boolean enabled) throws RemoteException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ethernetManager.get().setEthernetEnabled(enabled);
        } else {
            String[] ifaces = getAvailableInterfaces();
            for (String iface : ifaces) {
                setInterfaceEnabled(iface, enabled, null);
            }
        }
    }

    public static void setEthernetEnabledNoThrow(boolean enabled) {
        try {
            setEthernetEnabled(enabled);
        } catch (Throwable ignore) {
        }
    }

    public static boolean isEthernetEnabled() throws RemoteException {
        String[] ifaces = getAvailableInterfaces();
        for (String iface : ifaces) {
            boolean enabled = isInterfaceEnabled(iface);
            if (!enabled) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEthernetEnabledNoThrow() {
        try {
            return isEthernetEnabled();
        } catch (Throwable e) {
            return false;
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static void updateConfiguration(@NonNull String iface, EthernetNetworkUpdateRequest request,
                                           INetworkInterfaceOutcomeReceiver listener) throws RemoteException {
        ethernetManager.get().updateConfiguration(iface, request, listener);
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static void updateConfigurationNoThrow(@NonNull String iface, EthernetNetworkUpdateRequest request,
                                                  INetworkInterfaceOutcomeReceiver listener) {
        try {
            updateConfiguration(iface, request, listener);
        } catch (Throwable ignore) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static List<String> getInterfaceList() throws RemoteException {
        return ethernetManager.get().getInterfaceList();
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static List<String> getInterfaceListNoThrow() {
        try {
            return getInterfaceList();
        } catch (RemoteException e) {
            return new ArrayList<>();
        }
    }

    public static void setInterfaceEnabled(@NonNull String iface, boolean enabled, @Nullable INetworkInterfaceOutcomeReceiver listener)
            throws RemoteException {
        if (enabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ethernetManager.get().enableInterface(iface, listener);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ethernetManager.get().connectNetwork(iface, listener);
            } else {
                networkManagementService.get().setInterfaceUp(iface);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ethernetManager.get().disableInterface(iface, listener);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ethernetManager.get().disconnectNetwork(iface, listener);
            } else {
                networkManagementService.get().setInterfaceDown(iface);
            }
        }
    }

    public static void setInterfaceEnabledNoThrow(@NonNull String iface, boolean enabled, @Nullable INetworkInterfaceOutcomeReceiver listener) {
        try {
            setInterfaceEnabled(iface, enabled, listener);
        } catch (Throwable ignore) {
        }
    }

    public static boolean isInterfaceEnabled(@NonNull String iface)
            throws RemoteException {
        InterfaceConfiguration ifcg = networkManagementService.get().getInterfaceConfig(iface);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ifcg.isUp();
        }

        return ifcg.hasFlag(InterfaceConfiguration.FLAG_UP);
    }

    public static boolean isInterfaceEnabledNoThrow(@NonNull String iface) {
        try {
            return isInterfaceEnabled(iface);
        } catch (Throwable ignore) {
            return false;
        }
    }

    public static boolean isInterfaceActive(@NonNull String iface)
            throws RemoteException {
        InterfaceConfiguration ifcg = networkManagementService.get().getInterfaceConfig(iface);
        return ifcg.isActive();
    }

    public static boolean isInterfaceActiveNoThrow(@NonNull String iface) {
        try {
            return isInterfaceActive(iface);
        } catch (Throwable ignore) {
            return false;
        }
    }

    private static NetworkAddress getStaticNetworkAddress(@NonNull StaticIpConfigurationHidden configuration) {
        LinkAddress linkAddress = configuration.ipAddress;
        InetAddress address = linkAddress.getAddress();
        if (address == null) {
            return NetworkAddress.UNASSIGNED;
        }

        String ipAddress = address.getHostAddress();
        if (ipAddress == null) {
            return NetworkAddress.UNASSIGNED;
        }

        int netmaskInt = NetworkUtils.prefixLengthToNetmaskInt(linkAddress.getPrefixLength());
        InetAddress netmaskAddress = NetworkUtils.intToInetAddress(netmaskInt);
        if (netmaskAddress == null) {
            return NetworkAddress.UNASSIGNED;
        }

        String netmask = netmaskAddress.getHostAddress();
        if (netmask == null) {
            return NetworkAddress.UNASSIGNED;
        }

        InetAddress gatewayAddress = configuration.gateway;
        if (gatewayAddress == null) {
            return NetworkAddress.UNASSIGNED;
        }

        String gateway = gatewayAddress.getHostAddress();
        if (gateway == null) {
            return NetworkAddress.UNASSIGNED;
        }

        String dns1 = "";
        String dns2 = "";
        ArrayList<InetAddress> dnsServers = configuration.dnsServers;
        int size = dnsServers.size();
        if (size == 1) {
            InetAddress inetAddress = dnsServers.get(0);
            dns1 = inetAddress.getHostAddress();
        }

        if (size > 1) {
            InetAddress dns1Address = dnsServers.get(0);
            dns1 = dns1Address.getHostAddress();

            InetAddress dns2Address = dnsServers.get(1);
            dns2 = dns2Address.getHostAddress();
        }

        if (dns1 == null) {
            return NetworkAddress.UNASSIGNED;
        }

        return new NetworkAddress(
                NetworkAddress.IpAssignment.STATIC,
                ipAddress,
                netmask,
                gateway,
                dns1,
                dns2);
    }

    private static NetworkAddress getDhcpNetworkAddress(@NonNull String iface) throws RemoteException {
        IConnectivityManager manager = connectivityManager.get();
        Network[] networks = manager.getAllNetworks();
        LinkProperties linkProperties = null;
        for (Network network : networks) {
            NetworkCapabilities capabilities;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Context context = ActivityThread.currentApplication();
                capabilities = manager.getNetworkCapabilities(network, context.getOpPackageName(), context.getAttributionTag());
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Context context = ActivityThread.currentApplication();
                capabilities = manager.getNetworkCapabilities(network, context.getOpPackageName());
            } else {
                capabilities = manager.getNetworkCapabilities(network);
            }

            if (capabilities == null) {
                continue;
            }

            boolean hasTransport = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            if (!hasTransport) {
                continue;
            }

            LinkProperties lp = manager.getLinkProperties(network);
            if (lp == null) {
                continue;
            }

            if (!TextUtils.equals(iface, lp.getInterfaceName())) {
                continue;
            }

            linkProperties = lp;
            break;
        }

        if (linkProperties == null) {
            return NetworkAddress.UNASSIGNED;
        }

        List<LinkAddress> linkAddresses = linkProperties.getLinkAddresses();
        if (linkAddresses.isEmpty()) {
            return NetworkAddress.UNASSIGNED;
        }

        LinkAddress linkAddress = linkAddresses.get(0);
        InetAddress address = linkAddress.getAddress();
        if (!(address instanceof Inet4Address)) {
            return NetworkAddress.UNASSIGNED;
        }

        String ipAddress = address.getHostAddress();
        if (ipAddress == null) {
            return NetworkAddress.UNASSIGNED;
        }

        int netmaskInt = NetworkUtils.prefixLengthToNetmaskInt(linkAddress.getPrefixLength());
        InetAddress netmaskAddress = NetworkUtils.intToInetAddress(netmaskInt);
        if (netmaskAddress == null) {
            return NetworkAddress.UNASSIGNED;
        }

        String netmask = netmaskAddress.getHostAddress();
        if (netmask == null) {
            return NetworkAddress.UNASSIGNED;
        }

        List<RouteInfo> routes = linkProperties.getRoutes();
        InetAddress gatewayAddress = null;
        for (RouteInfo route : routes) {
            RouteInfoHidden routeInfoHidden = Refine.unsafeCast(route);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (route.hasGateway() && routeInfoHidden.isIPv4Default()) {
                    gatewayAddress = route.getGateway();
                    break;
                }
            } else {
                if (routeInfoHidden.hasGateway() && routeInfoHidden.isIPv4Default()) {
                    gatewayAddress = route.getGateway();
                    break;
                }
            }
        }

        if (gatewayAddress == null) {
            return NetworkAddress.UNASSIGNED;
        }

        String gateway = gatewayAddress.getHostAddress();
        if (gateway == null) {
            return NetworkAddress.UNASSIGNED;
        }

        String dns1 = "";
        String dns2 = "";
        List<InetAddress> dnsServers = linkProperties.getDnsServers();
        int size = dnsServers.size();
        if (size == 1) {
            InetAddress inetAddress = dnsServers.get(0);
            dns1 = inetAddress.getHostAddress();
        }

        if (size > 1) {
            InetAddress dns1Address = dnsServers.get(0);
            dns1 = dns1Address.getHostAddress();

            InetAddress dns2Address = dnsServers.get(1);
            dns2 = dns2Address.getHostAddress();
        }

        if (dns1 == null) {
            return NetworkAddress.UNASSIGNED;
        }

        return new NetworkAddress(
                NetworkAddress.IpAssignment.DHCP,
                ipAddress,
                netmask,
                gateway,
                dns1,
                dns2);
    }
}
