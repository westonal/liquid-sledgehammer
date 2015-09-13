package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class ListCommand implements Command {

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		for (FinancialTransaction transaction : state.getSource())
			out.println(formatTransaction(transaction));
	}

	protected String formatTransaction(FinancialTransaction transaction) {
		String groupPattern = transaction.getGroupPattern();
		if (groupPattern == null)
			groupPattern = "";
		return String.format("%s,%s,%s,%s,%s", transaction.getDate(),
				transaction.getDescription(), transaction.getValue(),
				transaction.getSource().getName(), groupPattern);
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    List");
	}
}
