package cz.dangelcz.qportforwarder.libs;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class NetHelper
{
	public static List<String> getLocalAddresses()
	{
		return getLocalActiveInterfaces().stream().map(i -> i.getHostAddress()).collect(Collectors.toList());
	}

	public static List<InetAddress> getLocalActiveInterfaces()
	{
		List<InetAddress> addresses = new ArrayList<>();

		try
		{
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements())
			{
				NetworkInterface iface = interfaces.nextElement();

				// filters out 127.0.0.1 and inactive interfaces
				if (!iface.isUp() || iface.isPointToPoint())
				{
					continue;
				}

				Enumeration<InetAddress> inetAddresses = iface.getInetAddresses();
				while (inetAddresses.hasMoreElements())
				{
					InetAddress inetAddress = inetAddresses.nextElement();
					String address = inetAddress.getHostAddress();
					if (isDecimalIP(address))
					{
						//addresses.add(new Pair<>(inetAddress.getHostName(), inetAddress.getHostAddress()));
						addresses.add(inetAddress);
					}

				}
			}
		}
		catch (SocketException se)
		{
			se.printStackTrace();
		}

		return addresses;
	}

	private static boolean isDecimalIP(String ip)
	{
		return ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
	}

	public static String getLocalHostname()
	{
		try
		{
			return InetAddress.getLocalHost().getHostName();
		}
		catch (UnknownHostException e)
		{
			throw new RuntimeException(e);
		}
	}
}
