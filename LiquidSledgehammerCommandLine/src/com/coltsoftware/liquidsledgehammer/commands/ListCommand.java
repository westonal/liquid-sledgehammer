package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class ListCommand implements Command {

	@Override
	public void execute(FinancialTreeNode root,
			FinancialTransactionSource source, Arguments arguments,
			PrintStream out) {
		for (FinancialTransaction transaction : source)
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
