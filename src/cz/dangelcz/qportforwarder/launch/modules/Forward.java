package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;
import cz.dangelcz.qportforwarder.logic.TcpForwarder;

/**
 * Command example:
 * qforward [from ip port] [to ip port] (protocol)
 * qforward from-ip:port to-ip:port upd/tcp
 * 
 * @author Daniel
 *
 */
@RunModule
public class Forward extends ARunModule
{
	@Override
	public void run(String[] args)
	{
		String localIp = getArgument(args, 1);
		int localPort = getIntArgument(args, 2);
		String targetIp = getArgument(args, 3);
		int targetPort = getIntArgument(args, 4);
		
		TcpForwarder forwarder = new TcpForwarder(localIp, localPort, targetIp, targetPort);
		forwarder.startForwarding();
	}

	@Override
	public String getHelpDescription()
	{
		return "Example: forward [from ip port] [to ip port] (protocol)";
	}

	@Override
	public String getRequiredParameters()
	{
		return "from_ip:port to_ip:port";
	}

	@Override
	public String getOptionalParameters()
	{
		return "protocol";
	}
}
