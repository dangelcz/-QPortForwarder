package cz.dangelcz.qportforwarder.data;

import java.util.List;

public class ApplicationConfigData
{
	private List<ForwardingParameters> parameters;
	private boolean darkMode;

	public boolean isDarkMode()
	{
		return darkMode;
	}

	public void setDarkMode(boolean darkMode)
	{
		this.darkMode = darkMode;
	}

	public List<ForwardingParameters> getParameters()
	{
		return parameters;
	}

	public void setParameters(List<ForwardingParameters> parameters)
	{
		this.parameters = parameters;
	}
}
