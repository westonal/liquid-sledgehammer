package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;

public final class ChangeNodeTreeCommand implements Command {

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		String path = arguments.second();
		FinancialTreeNode node = state.getCurrentNode();
		if ("~".equals(path))
			node = getRoot(node);
		else if ("..".equals(path))
			node = node.getParent();
		else
			node = node.findOrCreate(path);
		if (node != null)
			state.setCurrentNode(node);
	}

	protected FinancialTreeNode getRoot(FinancialTreeNode node) {
		while (node.getParent() != null)
			node = node.getParent();
		return node;
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    cd <Node>\tChange tree node");
	}
}
