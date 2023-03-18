package cz.dangelcz.qportforwarder.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class DataExchangeConnection
{
	private Socket clientSocket;
	private Socket targetSocket;

	private Logger logger = LogManager.getLogger(DataExchangeConnection.class);

	public DataExchangeConnection(Socket clientSocket, Socket targetSocket)
	{
		this.clientSocket = clientSocket;
		this.targetSocket = targetSocket;
	}

	public void open()
	{
		DataExchangeThread leftRight = new DataExchangeThread(this, clientSocket, targetSocket);
		DataExchangeThread rightLeft = new DataExchangeThread(this, targetSocket, clientSocket);

		leftRight.start();
		rightLeft.start();
	}

	public void close()
	{
		closeSocket(clientSocket);
		closeSocket(targetSocket);
	}

	private void closeSocket(Socket socket)
	{
		if (socket == null)
		{
			return;
		}

		synchronized (this)
		{
			if (!socket.isClosed())
			{
				try
				{
					socket.close();
				} catch (IOException e)
				{
					logger.error("Connection closed in data exchange thread", e);
				}
			}
		}
	}

}
