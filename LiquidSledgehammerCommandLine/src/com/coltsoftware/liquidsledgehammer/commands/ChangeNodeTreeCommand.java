package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;
import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;

public final class ChangeNodeTreeCommand extends PathCommandBase implements
		Command {

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		FinancialTreeNode node = state.getCurrentNode();
		String second = arguments.second();
		if ("".equals(second)) {
			out.println(fullPath(node));
			return;
		}
		node = findPath(out, node, second);
		if (node != null) {
			state.setCurrentNode(node);
			out.println(fullPath(node));
		}
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    cd                  \tShow node path");
		out.println("    cd <PartialNodePath>\tChange tree node");
		out.println("    cd ..               \tChange tree node to parent");
		out.println("    cd ~                \tChange tree node to root");
	}
}
