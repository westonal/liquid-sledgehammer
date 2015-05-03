package com.coltsoftware.liquidsledgehammer.filters;

import static com.coltsoftware.liquidsledgehammer.FilterHelper.filterSource;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.FilterFactory;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class SourceFilter implements FilterFactory {
	@Override
	public FinancialTransactionSource filter(FinancialTransactionSource source,
			Arguments arguments) {
		String sourceName = arguments.flagValue("s", "source");
		if (sourceName == null)
			return source;
		return filterSource(source, new TransactionSourceFilter.Builder()
				.caseInsensitive().sourceName(sourceName).build());
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    [-source/-s <sourceName>]");
	}
}
