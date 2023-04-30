package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.data.ForwardingParameters;
import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;
import cz.dangelcz.qportforwarder.logic.TcpForwarder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Command example:
 * forward [from ip port] [to ip port] (protocol)
 * forward from-ip:port to-ip:port upd/tcp
 *
 * @author Daniel
 */
@RunModule
public class Forward extends ARunModule
{
	private Logger logger = LogManager.getLogger(Forward.class);

	@Override
	public void run(ArgumentReader arguments)
	{
		ForwardingParameters parameters = readArguments(arguments);
		TcpForwarder forwarder;

		try
		{
			forwarder = new TcpForwarder(parameters);
		}
		catch (IOException e)
		{
			logger.error("Error while creating forwarder", e);
			return;
		}

		forwarder.startForwarding();
	}

	private ForwardingParameters readArguments(ArgumentReader arguments)
	{
		ForwardingParameters parameters = new ForwardingParameters();

		if (arguments.unreadArguments() == 2)
		{
			parameters.setLocalPort(arguments.getNextIntArgument());
			parameters.setTargetPort(arguments.getNextIntArgument());
			return parameters;
		}

		if (arguments.unreadArguments() == 3)
		{
			parameters.setLocalPort(arguments.getNextIntArgument());
			parameters.setTargetIp(arguments.getNextArgument());
			parameters.setTargetPort(arguments.getNextIntArgument());
			return parameters;
		}

		parameters.setLocalIp(arguments.getNextArgument());
		parameters.setLocalPort(arguments.getNextIntArgument());
		parameters.setTargetIp(arguments.getNextArgument());
		parameters.setTargetPort(arguments.getNextIntArgument());

		return parameters;
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
