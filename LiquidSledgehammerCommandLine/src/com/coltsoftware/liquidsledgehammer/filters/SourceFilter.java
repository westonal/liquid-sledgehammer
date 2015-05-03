package com.coltsoftware.liquidsledgehammer.filters;

import java.io.PrintStream;
import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.FilterFactory;

public final class SourceFilter implements FilterFactory {

	@Override
	public TransactionFilter constructFilter(String[] filterArguments) {
		ArrayList<TransactionFilter> filters = new ArrayList<TransactionFilter>();
		for (String filter : filterArguments)
			if (filter.startsWith("s:"))
				filters.add(buildSourceFilter(filter.substring(2)));
		return FilterCombine.orAll(filters);
	}

	protected static TransactionFilter buildSourceFilter(String filter) {
		return new TransactionSourceFilter.Builder().caseInsensitive()
				.sourceName(filter).build();
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    s:<sourceName>");
	}
}
