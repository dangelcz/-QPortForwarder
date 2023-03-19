package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.config.AppConfig;
import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.AppLauncher;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;

import java.util.Map;
import java.util.TreeSet;

@RunModule
public class Help extends ARunModule
{
	private Map<String, ARunModule> modules;

	@Override
	public void run(ArgumentReader arguments)
	{
		modules = AppLauncher.getModules();

		System.out.println("Application " + AppConfig.APP_VERSION + " syntax:");
		System.out.println("<command> <required parameter> (optional parameter) ...");
		System.out.println();

		if (arguments.size() == 2)
		{
			String argument = arguments.getNextArgument();
			ARunModule module = modules.get(argument);

			if (module == null)
			{
				System.err.print("Given command does not exists\n");
				AppLauncher.printSimilarCommands(argument);
				return;
			}

			module.printModuleHelp();
		} else
		{
			// print it sorted
			ARunModule module;
			for (String key : new TreeSet<>(modules.keySet()))
			{
				module = modules.get(key);
				module.printModuleHelp();
				System.out.println();
			}
		}
	}
	
	@Override
	public String getRequiredParameters()
	{
		return "";
	}

	@Override
	public String getOptionalParameters()
	{
		return "commandname";
	}

	@Override
	public String getHelpDescription()
	{
		return "prints description of all commands or given one";
	}
}
