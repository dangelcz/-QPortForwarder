package cz.dangelcz.qportforwarder.logic;

import cz.dangelcz.qportforwarder.data.ForwardingParameters;
import cz.dangelcz.qportforwarder.libs.ThreadProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class TcpForwarder
{
	protected ServerSocket localServerSocket;

	protected ForwardingParameters parameters;

	protected List<DataExchangeConnection> connections;

	private Logger logger = LogManager.getLogger(TcpForwarder.class);

	private boolean forwardingActive;

	public TcpForwarder()
	{
		connections = new ArrayList<>();
	}

	public TcpForwarder(ForwardingParameters parameters) throws IOException
	{
		connections = new ArrayList<>();
		resetParameters(parameters);
	}

	public void resetParameters(ForwardingParameters parameters) throws IOException
	{
		this.parameters = parameters;
		createServerSocket(parameters.getLocalIp(), parameters.getLocalPort());
	}

	private void createServerSocket(String localIp, int localPort) throws IOException
	{
		localServerSocket = new ServerSocket(localPort, 100, InetAddress.getByName(localIp));
	}

	public void startForwarding()
	{
		ThreadProvider.execute(this::forwarding);
	}

	private void forwarding()
	{
		logger.info("Forwarding from {}:{} to {}:{}",
				parameters.getLocalIp(),
				parameters.getLocalPort(),
				parameters.getTargetIp(),
				parameters.getTargetPort());

		forwardingActive = true;

		while (forwardingActive)
		{
			try
			{
				Socket clientSocket = localServerSocket.accept();
				Socket targetSocket = new Socket(parameters.getTargetIp(), parameters.getTargetPort());

				DataExchangeConnection connection = new DataExchangeConnection(clientSocket, targetSocket);
				connections.add(connection);
				connection.open();
			}
			catch (IOException e)
			{
				if (forwardingActive)
				{
					logger.error("Unable to start data exchange", e);
				}
			}
		}
	}

	public void stopForwarding()
	{
		forwardingActive = false;
		closeServerSocket();

		connections.forEach(c -> c.close());
		connections.clear();
	}

	private void closeServerSocket()
	{
		if (localServerSocket == null || localServerSocket.isClosed())
		{
			return;
		}

		try
		{
			localServerSocket.close();
		}
		catch (IOException e)
		{
			logger.error("Error closing server socket ", e);
		}
	}
}
