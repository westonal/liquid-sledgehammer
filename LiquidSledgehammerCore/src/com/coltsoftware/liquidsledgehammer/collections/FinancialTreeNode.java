package com.coltsoftware.liquidsledgehammer.collections;

import java.util.ArrayList;
import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;

public final class FinancialTreeNode implements Iterable<FinancialTreeNode> {

	private FinancialTreeNode parent;
	private final String name;
	private final SubTransactionList transactions = new SubTransactionList();
	private final ArrayList<FinancialTreeNode> children = new ArrayList<FinancialTreeNode>();

	public FinancialTreeNode(String name) {
		this.name = name;
	}

	public FinancialTreeNode() {
		this("");
	}

	public void add(SubTransaction transaction) {
		transactions.add(transaction);
	}

	public void add(FinancialTreeNode child) {
		if (child.getParent() != null)
			throw new TreeException();
		children.add(child);
		child.parent = this;
	}

	public Iterable<SubTransaction> getSubTransactions() {
		return transactions;
	}

	public Money getTotalValue() {
		Money totalValue = transactions.getTotalValue();
		for (FinancialTreeNode child : children)
			totalValue = totalValue.add(child.getTotalValue());
		return totalValue;
	}

	public FinancialTreeNode findOrCreate(String path) {
		if (path == "")
			return this;
		FinancialTreeNode inner = this;
		for (String name : path.split("\\."))
			inner = inner.findBySingleName(name);
		return inner;
	}

	private FinancialTreeNode findBySingleName(String name) {
		for (FinancialTreeNode child : children)
			if (child.getName().equals(name))
				return child;

		FinancialTreeNode financialTreeNode = new FinancialTreeNode(name);
		add(financialTreeNode);
		return financialTreeNode;
	}

	public String getName() {
		return name;
	}

	public FinancialTreeNode getParent() {
		return parent;
	}

	@Override
	public Iterator<FinancialTreeNode> iterator() {
		return children.iterator();
	}

	public void remove() {
		parent.children.remove(this);
		parent = null;
	}

	public String getFullPath() {
		StringBuilder sb = new StringBuilder();
		FinancialTreeNode node = this;
		sb.append(node.getNameOrRootSymbol());
		while (node.getParent() != null) {
			node = node.getParent();
			sb.insert(0, ".");
			sb.insert(0, node.getNameOrRootSymbol());
		}
		return sb.toString();
	}

	protected String getNameOrRootSymbol() {
		if (getParent() == null && getName().equals(""))
			return "~";
		return getName();
	}

	public FinancialTreeNode getRoot() {
		FinancialTreeNode node = this;
		while (node.getParent() != null)
			node = node.getParent();
		return node;
	}

}
