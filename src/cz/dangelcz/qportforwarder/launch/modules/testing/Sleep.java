package cz.dangelcz.qportforwarder.launch.modules.testing;


import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;
import cz.dangelcz.qportforwarder.libs.GeneralHelper;

@RunModule
public class Sleep extends ARunModule
{
	@Override
	public void run(ArgumentReader arguments)
	{
		int sleepInterval = 60;

		if (!arguments.isEmpty())
		{
			sleepInterval = arguments.getNextIntArgument();
		}

		sleep(sleepInterval);
	}
	
	private void sleep(int seconds)
	{
		GeneralHelper.sleepWithPrint(seconds);
	}

	@Override
	public String getHelpDescription()
	{
		return "sleeps for given amount of time and prints out countdown";
	}

	@Override
	public String getRequiredParameters()
	{
		return "";
	}

	@Override
	public String getOptionalParameters()
	{
		return "seconds";
	}
}
