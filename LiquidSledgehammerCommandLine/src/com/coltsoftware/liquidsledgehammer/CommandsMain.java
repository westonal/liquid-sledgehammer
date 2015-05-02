package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;
import com.coltsoftware.liquidsledgehammer.cmd.commands.BalanceCommand;

public final class CommandsMain {

	public static void main(String[] args) {
		Arguments arguments = new Arguments(args);

		registerSourceFactories();

		try {
			run(arguments);
		} catch (Exception ex) {
			printUsage();
			System.out.println(ex.toString());
		}
	}

	private static void printUsage() {
		System.out.println("Usage: ");
		printSources();
	}

	private static void printSources() {
		Context.printSources(System.out);
	}

	protected static void run(Arguments arguments) {
		Context context = Context.fromArgs(arguments);

		String commandFlag = arguments.flagValue("-c", "-command");
		if ("Balance".equals(commandFlag)) {
			new BalanceCommand(context);
		}
	}

	protected static void registerSourceFactories() {
		Context.registerSourceFactory("jsonin", new JsonSourceFatory());
	}

}
