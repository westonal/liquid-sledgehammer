package com.coltsoftware.liquidsledgehammer;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.balance.Balance;
import com.coltsoftware.liquidsledgehammer.cmd.Context;

public final class LocalBalanceCommand {

	private final Context context;

	public LocalBalanceCommand(Context context) {
		this.context = context;
	}

	public void execute(PrintStream out) {
		Balance balance = new Balance(PathSourceWalker.combineSources(context
				.getSources()));
		out.println(balance.getBalance());
	}

}
