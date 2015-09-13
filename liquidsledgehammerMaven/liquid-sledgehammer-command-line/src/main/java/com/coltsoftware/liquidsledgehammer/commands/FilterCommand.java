package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.FilterFactory;
import com.coltsoftware.liquidsledgehammer.FilterHelper;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.filters.BooleanTransactionFilter;
import com.coltsoftware.liquidsledgehammer.filters.FilterCombine;
import com.coltsoftware.liquidsledgehammer.filters.TransactionFilter;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class FilterCommand implements Command {

	private final HashMap<String, FilterFactory> filters;

	public FilterCommand(HashMap<String, FilterFactory> filters) {
		this.filters = filters;
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    filter remove");
		out.println("    filter <filterArgs>");
		printFilters(filters, out);
		out.println();
	}

	private static void printFilters(HashMap<String, FilterFactory> filters,
			PrintStream out) {
		for (String key : filters.keySet())
			filters.get(key).printUsage(out);
	}

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {

		FinancialTransactionSource source = state.getSource();

		if ("remove".equalsIgnoreCase(arguments.second())) {
			source = state.getUnfilteredSource();
			state.clearFilters();
		} else {
			TransactionFilter filter = constructFilters(filters,
					arguments.allAsArray());
			source = FilterHelper.filterSource(source, filter);
			state.setLatestFilter(filter);
		}

		state.setSource(source);
		
		out.println(state.getFilter());
	}

	public static TransactionFilter constructFilters(
			HashMap<String, FilterFactory> filters, String[] filterArguments) {

		if (filterArguments == null || filterArguments.length == 0)
			return BooleanTransactionFilter.TRUE;

		ArrayList<TransactionFilter> allFilters = new ArrayList<TransactionFilter>();

		for (String key : filters.keySet())
			allFilters.add(filters.get(key).constructFilter(filterArguments));
		return FilterCombine.andAll(allFilters);
	}

}
