package com.coltsoftware.liquidsledgehammer;

import static com.coltsoftware.liquidsledgehammer.FilterHelper.filterSource;

import java.io.PrintStream;
import java.util.HashMap;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;
import com.coltsoftware.liquidsledgehammer.commands.LocalBalanceCommand;
import com.coltsoftware.liquidsledgehammer.filters.TransactionSourceFilter;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class CommandsMain {

	public static HashMap<String, FilterFactory> filters = new HashMap<String, FilterFactory>();
	public static HashMap<String, Command> commands = new HashMap<String, Command>();

	public static void main(String[] args) {
		Arguments arguments = new Arguments(args);

		registerSourceFactories();
		registerFilters();
		registerCommands();

		try {
			run(arguments, System.out);
		} catch (Exception ex) {
			ex.printStackTrace();
			printUsage(System.out);
		}
	}

	protected static void run(Arguments arguments, PrintStream out) {
		Context context = Context.fromArgs(arguments);

		FinancialTransactionSource singleSource = PathSourceWalker
				.combineSources(context.getSources());

		singleSource = applyAllfilters(arguments, singleSource);

		String commandFlag = arguments.flagValue("c", "command");
		if (commandFlag == null)
			printUsage(out);
		for (String commandName : commands.keySet())
			if (commandName.equals(commandFlag))
				commands.get(commandName).execute(singleSource, arguments, out);
	}

	protected static FinancialTransactionSource applyAllfilters(
			Arguments arguments, FinancialTransactionSource singleSource) {
		for (String key : filters.keySet())
			singleSource = filters.get(key).filter(singleSource, arguments);
		return singleSource;
	}

	protected static void registerSourceFactories() {
		Context.registerSourceFactory("jsonin", new JsonSourceFatory());
	}

	private static void registerCommands() {
		commands.put("Balance", new LocalBalanceCommand());
	}

	private static void registerFilters() {
		filters.put("source", new FilterFactory() {

			@Override
			public FinancialTransactionSource filter(
					FinancialTransactionSource source, Arguments arguments) {
				String sourceName = arguments.flagValue("s", "source");
				if (sourceName == null)
					return source;
				return filterSource(source,
						new TransactionSourceFilter.Builder().caseInsensitive()
								.sourceName(sourceName).build());
			}

			@Override
			public void printUsage(PrintStream out) {
				out.println("    [-source/-s <sourceName>]");
			}

		});
	}

	private static void printUsage(PrintStream out) {
		out.println("Usage:");
		out.println();
		out.println("Specify a source:");
		printSources(out);
		out.println();
		out.println("Specify filters:");
		printFilters(out);
		out.println();
		out.println("Specify a command:");
		printCommands(out);
	}

	private static void printCommands(PrintStream out) {
		for (String key : commands.keySet())
			commands.get(key).printUsage(out);
	}

	private static void printFilters(PrintStream out) {
		for (String key : filters.keySet())
			filters.get(key).printUsage(out);
	}

	private static void printSources(PrintStream out) {
		Context.printSources(out);
	}

}
