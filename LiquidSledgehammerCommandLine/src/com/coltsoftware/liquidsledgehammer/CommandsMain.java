package com.coltsoftware.liquidsledgehammer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;
import com.coltsoftware.liquidsledgehammer.commands.LocalBalanceCommand;
import com.coltsoftware.liquidsledgehammer.filters.DateFilter;
import com.coltsoftware.liquidsledgehammer.filters.FilterCombine;
import com.coltsoftware.liquidsledgehammer.filters.SourceFilter;
import com.coltsoftware.liquidsledgehammer.filters.TransactionFilter;
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
		String commandFlag = arguments.flagValue("c", "command");
		if (commandFlag == null) {
			printUsage(out);
			return;
		}

		TransactionFilter filter = constructFilters(arguments);

		Context context = Context.fromArgs(arguments);

		FinancialTransactionSource singleSource = PathSourceWalker
				.combineSources(context.getSources());

		singleSource = FilterHelper.filterSource(singleSource, filter);

		for (String commandName : commands.keySet())
			if (commandName.equals(commandFlag))
				commands.get(commandName).execute(singleSource, arguments, out);
	}

	protected static TransactionFilter constructFilters(Arguments arguments) {
		String[] filterArguments = arguments.flagValues("f", "filter");

		ArrayList<TransactionFilter> allFilters = new ArrayList<TransactionFilter>();

		for (String key : filters.keySet())
			allFilters.add(filters.get(key).constructFilter(filterArguments));
		return FilterCombine.andAll(allFilters);
	}

	protected static void registerSourceFactories() {
		Context.registerSourceFactory("jsonin", new JsonSourceFatory());
	}

	private static void registerCommands() {
		commands.put("Balance", new LocalBalanceCommand());
	}

	private static void registerFilters() {
		filters.put("source", new SourceFilter());
		filters.put("date", new DateFilter());
	}

	private static void printUsage(PrintStream out) {
		out.println("Usage:");
		out.println();
		out.println("Specify a source:");
		printSources(out);
		out.println();
		out.println("Specify filters:");
		out.println("    -f/-filter");
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
