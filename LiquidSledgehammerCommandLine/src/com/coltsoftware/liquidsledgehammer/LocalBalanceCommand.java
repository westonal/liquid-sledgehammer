package com.coltsoftware.liquidsledgehammer;

import static com.coltsoftware.liquidsledgehammer.FilterHelper.filterSource;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.balance.Balance;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;
import com.coltsoftware.liquidsledgehammer.filters.TransactionSourceFilter;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class LocalBalanceCommand {

	private final Context context;

	public LocalBalanceCommand(Context context) {
		this.context = context;
	}

	public void execute(Arguments arguments, PrintStream out) {
		FinancialTransactionSource singleSource = PathSourceWalker
				.combineSources(context.getSources());

		String sourceName = arguments.flagValue("s", "source");
		if (sourceName != null) {
			singleSource = filterSource(singleSource,
					new TransactionSourceFilter.Builder().caseInsensitive()
							.sourceName(sourceName).build());
		}

		Balance balance = new Balance(singleSource);
		out.println(balance.getBalance());
	}
}
