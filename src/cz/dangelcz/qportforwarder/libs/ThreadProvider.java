package cz.dangelcz.qportforwarder.libs;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadProvider
{
	public static void execute(Runnable r)
	{
		getInstance().getExecutor().execute(r);
	}

	private static ThreadProvider getInstance()
	{
		// If instance is not yet created
		if (instance != null)
		{
			return instance;
		}

		// Synchronize for thread-safety
		synchronized (ThreadProvider.class)
		{
			// Double-check for null
			if (instance == null)
			{
				instance = new ThreadProvider();
			}

			return instance;
		}
	}

	// Singleton instance
	private static ThreadProvider instance;

	private Executor executor;

	private ThreadProvider()
	{
		executor = Executors.newCachedThreadPool();
	}

	private Executor getExecutor()
	{
		return executor;
	}
}
