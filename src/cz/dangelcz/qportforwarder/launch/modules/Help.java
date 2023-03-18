package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.config.AppConfig;
import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.AppLauncher;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;

import java.util.Map;
import java.util.TreeSet;

@RunModule
public class Help extends ARunModule
{
	private Map<String, ARunModule> modules;

	@Override
	public void run(String[] args)
	{
		modules = AppLauncher.getModules();

		System.out.println("Scriptator " + AppConfig.APP_VERSION + " syntax:");
		System.out.println("<command> <required parameter> (optional parameter) ...");
		System.out.println();

		if (args.length == 2)
		{
			ARunModule module = modules.get(args[1]);

			if (module == null)
			{
				System.err.print("Given command does not exists\n");
				AppLauncher.printSimilarCommands(args[1]);
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
