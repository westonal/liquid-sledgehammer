package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.balance.Balance;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;

public final class LocalBalanceCommand implements Command {

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		Balance balance = new Balance(state.getSource());
		out.println(balance.getBalance());
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    Balance");
	}
}
