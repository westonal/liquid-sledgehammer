package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;

public final class ListTreeCommand extends PathCommandBase implements Command {

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		FinancialTreeNode treeNode = state.getCurrentNode();

		String path = arguments.second();
		treeNode = findPath(out, treeNode, path);

		out.println(formatTopNode(treeNode));
		for (FinancialTreeNode node : treeNode)
			out.println(formatNode(node));
		for (SubTransaction subTransaction : treeNode.getSubTransactions())
			out.println(formatSubTransaction(subTransaction));
	}

	private static String formatTopNode(FinancialTreeNode node) {
		return String.format("[%s %s]", node.getName(), node.getTotalValue());
	}

	private static String formatSubTransaction(SubTransaction subTransaction) {
		FinancialTransaction transaction = subTransaction.getTransaction();
		return String.format("%s\t%s\t%s\t%s", transaction.getDate(),
				transaction.getSource().getName(),
				transaction.getDescription(), subTransaction.getValue());
	}

	private static String formatNode(FinancialTreeNode node) {
		return String.format("%s %s", node.getName(), node.getTotalValue());
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    ls \tList tree");
		out.println("    ls <PartialNodePath>\tList tree under <PartialNodePath>");
	}
}
