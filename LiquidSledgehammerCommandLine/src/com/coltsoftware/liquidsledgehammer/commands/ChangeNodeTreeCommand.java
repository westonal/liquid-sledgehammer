package com.coltsoftware.liquidsledgehammer.commands;

import java.io.PrintStream;
import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;

public final class ChangeNodeTreeCommand implements Command {

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		FinancialTreeNode node = state.getCurrentNode();
		String second = arguments.second();
		if ("".equals(second)) {
			out.println(fullPath(node));
			return;
		}
		String[] paths = "..".equals(second) ? new String[] { second } : second
				.split("[./]");
		for (String path : paths) {
			if ("~".equals(path))
				node = getRoot(node);
			else if ("..".equals(path))
				node = node.getParent();
			else {
				FinancialTreeNode newNode = findChild(node, path, out);
				if (newNode == node)
					break;
				node = newNode;
			}
		}
		if (node != null) {
			state.setCurrentNode(node);
			out.println(fullPath(node));
		}
	}

	private static String fullPath(FinancialTreeNode node) {
		StringBuilder sb = new StringBuilder();
		sb.append(node.getName());
		while (node.getParent() != null) {
			node = node.getParent();
			sb.insert(0, ".");
			sb.insert(0, node.getName());
		}
		return sb.toString();
	}

	protected FinancialTreeNode findChild(FinancialTreeNode node, String path,
			PrintStream out) {
		ArrayList<FinancialTreeNode> possibles = new ArrayList<FinancialTreeNode>();
		for (FinancialTreeNode child : node)
			if (child.getName().startsWith(path))
				possibles.add(child);
		switch (possibles.size()) {
		case 1:
			return possibles.get(0);
		case 0: {
			out.println(String.format(
					"Can't find any match for \"%s\" in \"%s\"", path,
					node.getName()));
			return node;
		}
		default: {
			out.println(String.format("Multiple matches for \"%s\" in \"%s\"",
					path, node.getName()));
			for (FinancialTreeNode possible : possibles)
				out.println(possible.getName());
			return node;
		}
		}
	}

	protected FinancialTreeNode getRoot(FinancialTreeNode node) {
		while (node.getParent() != null)
			node = node.getParent();
		return node;
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    cd                  \tShow node path");
		out.println("    cd <PartialNodePath>\tChange tree node");
		out.println("    cd ..               \tChange tree node to parent");
		out.println("    cd ~                \tChange tree node to root");
	}
}
