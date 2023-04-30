package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.config.AppConfig;
import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.AppLauncher;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;
import cz.dangelcz.qportforwarder.libs.GeneralHelper;

@RunModule
public class Version extends ARunModule
{
	@Override
	public void run(ArgumentReader arguments)
	{
		System.out.println(GeneralHelper.getPomXmlVersion());
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
		return "prints current application version";
	}
}
