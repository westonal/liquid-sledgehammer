package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.balance.Balance;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class LocalBalanceCommand implements Command {

	@Override
	public void execute(FinancialTransactionSource source, Arguments arguments,
			PrintStream out) {
		Balance balance = new Balance(source);
		out.println(balance.getBalance());
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    -c Balance");
	}
}
