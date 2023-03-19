package cz.dangelcz.qportforwarder.launch;


import cz.dangelcz.qportforwarder.launch.annotation.RunModule;
import cz.dangelcz.qportforwarder.launch.exceptions.ValidationException;
import cz.dangelcz.qportforwarder.libs.GeneralHelper;

import java.util.Arrays;
import java.util.List;

import static cz.dangelcz.qportforwarder.libs.GeneralHelper.INNL;

public abstract class ARunModule
{
	public String getCommandName()
	{
		RunModule annotation = this.getClass().getAnnotation(RunModule.class);
		String commandName = annotation.value();

		if (GeneralHelper.INL(commandName))
		{
			commandName = this.getClass().getSimpleName().toLowerCase();
		}

		return commandName;
	}
	
	public void printModuleHelp()
	{
		String required = String.join("> <", getRequiredParameters().split(" "));
		required = INNL(required) ? " <" + required + ">" : "";

		String optional = String.join(") (", getOptionalParameters().split(" "));
		optional = INNL(optional) ? " (" + optional + ")" : "";

		String helpDescription = getHelpDescription().replace("\n", "\n\t\t");
		
		System.out.format("%s%s%s\n\t: %s",	getCommandName(),
												required,
												optional,
												helpDescription);
	}

	protected List<String> fromString(String separatedLine)
	{
		return Arrays.asList(separatedLine.split(" "));
	}

	public abstract void run(ArgumentReader arguments);

	public abstract String getRequiredParameters();

	public abstract String getOptionalParameters();

	public abstract String getHelpDescription();
}
