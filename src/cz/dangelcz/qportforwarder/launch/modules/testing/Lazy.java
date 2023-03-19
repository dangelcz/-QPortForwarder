package cz.dangelcz.qportforwarder.launch.modules.testing;



import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.AppLauncher;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;

import java.util.Scanner;

/**
 * Just stops the program at the begginnig, then launches module by given parameters
 * 
 * Good for performance debugging - pause before full start
 * 
 * @author Daniel
 *
 */
@RunModule
public class Lazy extends ARunModule
{
	@Override
	public void run(ArgumentReader arguments)
	{
		System.out.println("Press enter to continue ...");

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		sc.nextLine();

		String[] newArgs = new String[arguments.size() - 1];
		for (int i = 0; i < newArgs.length; i++)
		{
			newArgs[i] = arguments.getArgument(i + 1);
		}

		AppLauncher.main(newArgs);
	}

	@Override
	public String getHelpDescription()
	{
		return "just stops the program at the begginnig, then launches module by given parameters";
	}

	@Override
	public String getRequiredParameters()
	{
		return "parameters of another module";
	}

	@Override
	public String getOptionalParameters()
	{
		return "";
	}
}
