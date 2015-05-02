package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;
import com.coltsoftware.liquidsledgehammer.cmd.commands.BalanceCommand;

public final class CommandsMain {

	public static void main(String[] args) {
		Arguments arguments = new Arguments(args);

		registerSourceFactories();

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
