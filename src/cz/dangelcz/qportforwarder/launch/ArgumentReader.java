package cz.dangelcz.qportforwarder.launch;

import cz.dangelcz.qportforwarder.launch.exceptions.ValidationException;

import java.util.Arrays;
import java.util.List;

public class ArgumentReader
{
    private List<String> arguments;
    private int argumentIndex;

    public ArgumentReader(String[] args)
    {
        this.arguments = Arrays.asList(args);
        this.argumentIndex = 0;
    }

    public void resetIndex()
    {
        argumentIndex = 0;
    }

    public void setArgumentIndex(int argumentIndex)
    {
        this.argumentIndex = argumentIndex;
    }

    public String getNextArgument()
    {
        return getArgument(argumentIndex++);
    }

    public String getNextArgumentOrDefault(String defaultValue)
    {
        return getArgumentOrDefault(argumentIndex++, defaultValue);
    }

    public int getNextIntArgument()
    {
        return getIntArgument(argumentIndex++);
    }

    public int getNextIntArgumentOrDefault(int defaultValue)
    {
        return getIntArgumentOrDefault(argumentIndex++, defaultValue);
    }

    public String getArgument(int index)
    {
        if (arguments.size() <= index)
        {
            throw new ValidationException("Missing argument number: " + index);
        }

        return arguments.get(index);
    }

    public String getArgumentOrDefault(int index, String defaultValue)
    {
        if (arguments.size() <= index)
        {
            return defaultValue;
        }

        return arguments.get(index);
    }

    public int getIntArgument(int index)
    {
        if (arguments.size() <= index)
        {
            throw new ValidationException("Missing argument number: " + index);
        }

        try
        {
            return Integer.parseInt(arguments.get(index));
        }
        catch (NumberFormatException e)
        {
            throw new ValidationException("Given parameter is not a number");
        }
    }

    public int getIntArgumentOrDefault(int index, int defaultValue)
    {
        if (arguments.size() <= index)
        {
            return defaultValue;
        }

        try
        {
            return Integer.parseInt(arguments.get(index));
        }
        catch (NumberFormatException e)
        {
            throw new ValidationException("Given parameter is not a number");
        }
    }

    public boolean isEmpty()
    {
        return arguments.isEmpty();
    }

    public int size()
    {
        return arguments.size();
    }

    public int unreadArguments()
    {
        return size() - argumentIndex;
    }
}
