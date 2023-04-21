package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.gui.WindowApplication;
import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;

@RunModule(value = "start-gui", isDefault = true)
public class StartGui extends ARunModule
{
	@Override
	public void run(ArgumentReader arguments)
	{
		WindowApplication.launch(WindowApplication.class);
	}

	@Override
	public String getRequiredParameters()
	{
		return "";
	}

	@Override
	public String getOptionalParameters()
	{
		return "";
	}

	@Override
	public String getHelpDescription()
	{
		return "Starts application with graphic user interface. This is default command.";
	}
}
