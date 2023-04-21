package cz.dangelcz.qportforwarder.data;

import java.io.Serializable;

public class ForwardingParameters implements Serializable
{
	private String localIp = "0.0.0.0";
	private int localPort;
	private String targetIp = "localhost";
	private int targetPort;

	private String comment;


	public ForwardingParameters()
	{
		// just empty constructor
	}

	public ForwardingParameters(String localIp, int localPort, String targetIp, int targetPort)
	{
		this.localIp = localIp;
		this.localPort = localPort;
		this.targetIp = targetIp;
		this.targetPort = targetPort;
	}

	public String getLocalIp()
	{
		return localIp;
	}

	public void setLocalIp(String localIp)
	{
		this.localIp = localIp;
	}

	public int getLocalPort()
	{
		return localPort;
	}

	public void setLocalPort(int localPort)
	{
		this.localPort = localPort;
	}

	public String getTargetIp()
	{
		return targetIp;
	}

	public void setTargetIp(String targetIp)
	{
		this.targetIp = targetIp;
	}

	public int getTargetPort()
	{
		return targetPort;
	}

	public void setTargetPort(int targetPort)
	{
		this.targetPort = targetPort;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}


	@Override
	public String toString()
	{
		return "ForwardingParameters{" +
				"localIp='" + localIp + '\'' +
				", localPort=" + localPort +
				", targetIp='" + targetIp + '\'' +
				", targetPort=" + targetPort + '\'' +
				", comment=" + comment +
				'}';
	}
}
