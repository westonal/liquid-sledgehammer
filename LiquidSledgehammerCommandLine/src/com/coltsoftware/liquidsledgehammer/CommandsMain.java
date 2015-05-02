package com.coltsoftware.liquidsledgehammer;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;

public final class CommandsMain {

	public static void main(String[] args) {
		Arguments arguments = new Arguments(args);

		registerSourceFactories();

		try {
			run(arguments);
		} catch (Exception ex) {
			ex.printStackTrace();
			printUsage(System.out);
		}
	}

	private static void printUsage(PrintStream out) {
		out.println("Usage: ");
		printSources(out);
	}

	private static void printSources(PrintStream out) {
		Context.printSources(out);
	}

	protected static void run(Arguments arguments) {
		Context context = Context.fromArgs(arguments);

		String commandFlag = arguments.flagValue("c", "command");
		if ("Balance".equals(commandFlag)) {
			new LocalBalanceCommand(context).execute(arguments, System.out);
		}
	}

	protected static void registerSourceFactories() {
		Context.registerSourceFactory("jsonin", new JsonSourceFatory());
	}

}
