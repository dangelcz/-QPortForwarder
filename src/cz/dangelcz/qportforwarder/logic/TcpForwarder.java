package cz.dangelcz.qportforwarder.logic;

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

	protected String localIp;
	protected int localPort;
	protected String targetIp;
	protected int targetPort;

	protected List<DataExchangeConnection> connections;

	private Logger logger = LogManager.getLogger(TcpForwarder.class);

	public TcpForwarder(String localIp, int localPort, String targetIp, int targetPort)
	{
		this.localIp = localIp;
		this.localPort = localPort;
		this.targetIp = targetIp;
		this.targetPort = targetPort;

		connections = new ArrayList<>();

		createServerSocket(localIp, localPort);
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
		while (true)
		{
			try
			{
				Socket clientSocket = localServerSocket.accept();
				Socket targetSocket = new Socket(targetIp, targetPort);

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
