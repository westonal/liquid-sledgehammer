package com.coltsoftware.liquidsledgehammer.filters;

import static com.coltsoftware.liquidsledgehammer.FilterHelper.filterSource;

import java.io.PrintStream;
import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.FilterFactory;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class SourceFilter implements FilterFactory {
	@Override
	public FinancialTransactionSource filter(FinancialTransactionSource source,
			String[] filterArguments) {
		ArrayList<TransactionFilter> filters = new ArrayList<TransactionFilter>();
		for (String filter : filterArguments)
			if (filter.startsWith("s:"))
				filters.add(new TransactionSourceFilter.Builder()
						.caseInsensitive().sourceName(filter.substring(2))
						.build());
		if (filters.isEmpty())
			return source;
		return filterSource(source, orAll(filters));
	}

	private TransactionFilter orAll(ArrayList<TransactionFilter> filters) {
		TransactionFilter composite = filters.get(filters.size() - 1);
		for (int i = filters.size() - 2; i >= 0; i--)
			composite = LogicTransactionFilter.or(filters.get(i), composite);
		return composite;
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    s:<sourceName>");
	}
}
