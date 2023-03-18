package cz.dangelcz.qportforwarder.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static cz.dangelcz.qportforwarder.config.AppConfig.DATA_EXCHANGE_BUFFER_SIZE;

public class DataExchangeThread extends Thread
{
	private InputStream inputStream;
	private OutputStream outputStream;

	private DataExchangeConnection connection;

	private Logger logger = LogManager.getLogger(DataExchangeThread.class);
	
	public DataExchangeThread(DataExchangeConnection dataExchangeConnection, Socket fromSocket, Socket toSocket)
	{
		try
		{
			this.connection = dataExchangeConnection;
			
			inputStream = fromSocket.getInputStream();
			outputStream = toSocket.getOutputStream();
		} catch (IOException e)
		{
			logger.error("Unable to get streams from sockets", e);
		}
	}

	public void run()
	{
		byte[] dataBuffer = new byte[DATA_EXCHANGE_BUFFER_SIZE];
		int bytesRead;

		try
		{
			while ((bytesRead = inputStream.read(dataBuffer)) != -1)
			{
				outputStream.write(dataBuffer, 0, bytesRead);
			}
		} catch (IOException e)
		{
			logger.info("Connection closed in data exchange thread", e);
		}
		
		connection.close();
	}
}