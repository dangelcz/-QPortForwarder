package cz.dangelcz.qportforwarder.logic;

import cz.dangelcz.qportforwarder.data.ForwardingParameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TcpForwarder
{
	protected ServerSocket localServerSocket;

	protected ForwardingParameters parameters;

	protected List<DataExchangeConnection> connections;

	private Logger logger = LogManager.getLogger(TcpForwarder.class);

	public TcpForwarder(ForwardingParameters parameters)
	{
		this.parameters = parameters;

		connections = new ArrayList<>();

		createServerSocket(parameters.getLocalIp(), parameters.getLocalPort());
	}

	private void createServerSocket(String localIp, int localPort)
	{
		try
		{
			localServerSocket = new ServerSocket(localPort, 100, InetAddress.getByName(localIp));
		} catch (IOException e)
		{
			logger.error("Unable to create socket", e);
		}
	}

	public void startForwarding()
	{
		logger.info("Forwarding from {}:{} to {}:{}",
				parameters.getLocalIp(),
				parameters.getLocalPort(),
				parameters.getTargetIp(),
				parameters.getTargetPort());

		while (true)
		{
			try
			{
				Socket clientSocket = localServerSocket.accept();
				Socket targetSocket = new Socket(parameters.getTargetIp(), parameters.getTargetPort());

				DataExchangeConnection connection = new DataExchangeConnection(clientSocket, targetSocket);
				connections.add(connection);
				connection.open();

			} catch (IOException e)
			{
				logger.error("Unable to start data exchange", e);
			}
		}
	}

}
