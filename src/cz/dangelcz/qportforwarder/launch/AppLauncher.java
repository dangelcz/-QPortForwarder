package cz.dangelcz.qportforwarder.launch;


import cz.dangelcz.qportforwarder.launch.annotation.RunModule;
import cz.dangelcz.qportforwarder.launch.exceptions.ValidationException;
import cz.dangelcz.qportforwarder.libs.PackageReflection;
import cz.dangelcz.qportforwarder.libs.PackageReflection.*;
import cz.dangelcz.qportforwarder.libs.Reflection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AppLauncher
{
	private static Map<String, ARunModule> modules;

	private static final String MODULES_PACKAGE = ARunModule.class.getPackage().getName() + ".modules";
	private static final String TEST_MODULES_PACKAGE = ARunModule.class.getPackage().getName() + ".modules.testing";

	private static Logger logger = LogManager.getLogger(AppLauncher.class);

	public static void main(String[] args)
	{
		try
		{
			loadModules();
			launchModule(args);
		}
		catch (Exception e)
		{
			// log application failure error
			logger.error(e);
		}
	}

	public static Map<String, ARunModule> getModules()
	{
		return modules;
	}

	private static ARunModule defaultModule;

	private static void loadModules() throws InstantiationException, IllegalAccessException
	{
		if (modules != null)
		{
			return;
		}

		modules = new HashMap<>();

		List<Class<?>> modulesClasses = PackageReflection.getClassesOfPackage(MODULES_PACKAGE, ClassCriteria.BY_ANNOTATION, RunModule.class);
		List<Class<?>> testsModulesClasses = PackageReflection.getClassesOfPackage(TEST_MODULES_PACKAGE, ClassCriteria.BY_ANNOTATION, RunModule.class);
		modulesClasses.addAll(testsModulesClasses);

		ARunModule module;
		for (Class<?> clazz : modulesClasses)
		{
			module = (ARunModule) clazz.newInstance();
			checkDefaultModule(module);
			modules.put(module.getCommandName(), module);
		}
	}

	private static void checkDefaultModule(ARunModule module)
	{
		RunModule annotation = module.getClass().getAnnotation(RunModule.class);

		if (!annotation.isDefault())
		{
			return;
		}

		if (defaultModule == null)
		{
			defaultModule = module;
		}
		else
		{
			throw new Error("Multiple default modules found");
		}

	}

	private static void launchModule(String[] args)
	{
		ArgumentReader arguments = new ArgumentReader(args);
		ARunModule module;
		if (arguments.isEmpty() && defaultModule == null)
		{
			printBadCommand();
			return;
		}

		String command = arguments.getArgument(0);
		module = modules.getOrDefault(command.toLowerCase(), null);

		if (module == null)
		{
			if (defaultModule != null)
			{
				module = defaultModule;
			}
			else
			{
				printBadCommand(command);
				return;
			}
		}
		else
		{
			// move index from first to second, so submodule can use "next" method
			arguments.setArgumentIndex(1);
		}

		try
		{
			module.run(arguments);
		}
		catch (ValidationException e)
		{
			System.err.println(e.getMessage());
			System.out.println();
			module.printModuleHelp();
		}
	}

	private static void printBadCommand()
	{
		System.err.print("Bad command. Use 'help' parameter for help.\n");
	}

	private static void printBadCommand(String commandName)
	{
		printBadCommand();
		printSimilarCommands(commandName);
	}

	public static void printSimilarCommands(String commandName)
	{
		List<String> similarities = getSimilarCommands(commandName);

		if (similarities.size() > 0)
		{
			System.out.println("Similar commands:");
		}

		System.out.println(String.join(" ", similarities));
	}

	protected static List<String> getSimilarCommands(String target)
	{
		target = target.toLowerCase().trim();

		List<String> similarities = new ArrayList<>();
		for (String moduleName : modules.keySet())
		{
			if (moduleName.contains(target) || target.contains(moduleName))
			{
				similarities.add(moduleName);
			}
		}

		return similarities;
	}
}
