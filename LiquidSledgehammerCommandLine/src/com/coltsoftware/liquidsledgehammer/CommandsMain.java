package com.coltsoftware.liquidsledgehammer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;
import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.commands.ChangeNodeTreeCommand;
import com.coltsoftware.liquidsledgehammer.commands.ListCommand;
import com.coltsoftware.liquidsledgehammer.commands.ListTreeCommand;
import com.coltsoftware.liquidsledgehammer.commands.PromptCommand;
import com.coltsoftware.liquidsledgehammer.commands.LocalBalanceCommand;
import com.coltsoftware.liquidsledgehammer.filters.DateFilter;
import com.coltsoftware.liquidsledgehammer.filters.FilterCombine;
import com.coltsoftware.liquidsledgehammer.filters.SourceFilter;
import com.coltsoftware.liquidsledgehammer.filters.TransactionFilter;
import com.coltsoftware.liquidsledgehammer.groups.JsonGroupsFactory;
import com.coltsoftware.liquidsledgehammer.processing.Processor;
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

	protected static void run(Arguments arguments, PrintStream out)
			throws IOException {
		String commandFlag = arguments.flagValue("c", "command");
		if (commandFlag == null) {
			printUsage(out);
			return;
		}
		String jsonGroups = arguments.flagValue("jsongroup");
		if (jsonGroups == null) {
			printUsage(out);
			return;
		}

		TransactionFilter filter = constructFilters(arguments);

		Context context = Context.fromArgs(arguments);

		FinancialTransactionSource singleSource = PathSourceWalker
				.combineSources(context.getSources());

		singleSource = FilterHelper.filterSource(singleSource, filter);

		FinancialTreeNode root = createTree(singleSource, new File(jsonGroups));

		State state = new State();
		state.setCurrentNode(root);
		state.setSource(singleSource);

		for (String commandName : commands.keySet())
			if (commandName.equals(commandFlag))
				commands.get(commandName).execute(state, arguments, out);
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
		commands.put("List", new ListCommand());
		commands.put("ls", new ListTreeCommand());
		commands.put("cd", new ChangeNodeTreeCommand());
		commands.put("prompt", new PromptCommand(commands));
	}

	private static void registerFilters() {
		filters.put("source", new SourceFilter());
		filters.put("date", new DateFilter());
	}

	private static void printUsage(PrintStream out) {
		out.println("Usage:");
		out.println();
		out.println("Specify a source:");
		out.println("    -jsongroup <groupfile>");
		printSources(out);
		out.println();
		out.println("Specify filters:");
		out.println("  -f/-filter");
		printFilters(out);
		out.println();
		out.println("Specify a command:");
		out.println("  -c/-command");
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

	private static FinancialTreeNode createTree(
			FinancialTransactionSource transactionSource, File groupsPath)
			throws IOException {
		AliasPathResolver aliasPathResolver = JsonGroupsFactory
				.createAliasPathResolver(groupsPath);
		Processor processor = new Processor(aliasPathResolver,
				JsonGroupsFactory.createSubTransactionFactory(groupsPath));
		FinancialTreeNode root = new FinancialTreeNode();
		processor.populateTree(transactionSource, root);
		return root;
	}

}
