package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.AppLauncher;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;

import java.util.NoSuchElementException;
import java.util.Scanner;


@RunModule
public class Console extends ARunModule
{
	@Override
	public void run(ArgumentReader arguments)
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		while (true)
		{
			try
			{
				System.out.println("\n-----------------------");
				System.out.println("Run your command (or 'exit'):");
				System.out.print("> ");
				
				// String command = System.console().readLine();
				String command = sc.nextLine();
				
				if ("exit".equalsIgnoreCase(command))
				{
					return;
				}
				
				AppLauncher.main(command.split(" "));
			} catch (NoSuchElementException e)
			{
				return;
			} catch (Exception e)
			{
				System.err.println("You got exception, but console runs again!");
			}
		}
		
		//sc.close();
	}

	@Override
	public String getHelpDescription()
	{
		return "developer tool - reruns given command";
	}

	@Override
	public String getRequiredParameters()
	{
		return "any";
	}

	@Override
	public String getOptionalParameters()
	{
		return "any";
	}
}
