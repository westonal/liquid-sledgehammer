package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class ListTreeCommand implements Command {

	@Override
	public void execute(FinancialTreeNode root,
			FinancialTransactionSource source, Arguments arguments,
			PrintStream out) {
		for (FinancialTreeNode node : root)
			out.println(formatNode(node));
	}

	private String formatNode(FinancialTreeNode node) {
		return String.format("%s %s", node.getName(), node.getTotalValue());
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    ls\tList tree");
	}
}
