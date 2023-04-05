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

	private boolean forwardingActive;

	private Thread forwardingThread;

	public TcpForwarder()
	{
		connections = new ArrayList<>();
	}

	public TcpForwarder(ForwardingParameters parameters)
	{
		connections = new ArrayList<>();
		resetParameters(parameters);
	}

	public void resetParameters(ForwardingParameters parameters)
	{
		this.parameters = parameters;
		createServerSocket(parameters.getLocalIp(), parameters.getLocalPort());
	}

	private void createServerSocket(String localIp, int localPort)
	{
		try
		{
			localServerSocket = new ServerSocket(localPort, 100, InetAddress.getByName(localIp));
		}
		catch (IOException e)
		{
			logger.error("Unable to create socket", e);
		}
	}

	public void startForwarding()
	{
		forwardingThread = new Thread(this::forwarding);
		forwardingThread.setName("Forwarding thread");
		forwardingThread.start();
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

		try
		{
			forwardingThread.join();
		}
		catch (InterruptedException e)
		{
			logger.error("Error while waiting for main forwarding thread end", e);
		}
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
