package com.coltsoftware.liquidsledgehammer.collections;

import java.util.ArrayList;
import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class FinancialTreeNode {

	private FinancialTreeNode parent;
	private final String name;
	private final FinancialTransactionList transactions = new FinancialTransactionList();
	private final ArrayList<FinancialTreeNode> children = new ArrayList<FinancialTreeNode>();

	public FinancialTreeNode(String name) {
		this.name = name;
	}

	public FinancialTreeNode() {
		this("");
	}

	public void add(FinancialTransaction transaction) {
		transactions.add(transaction);
	}

	public void add(FinancialTreeNode child) {
		if (child.getParent() != null)
			throw new TreeException();
		children.add(child);
		child.parent = this;
	}

	public Iterator<FinancialTransaction> getTransactions() {
		return transactions.iterator();
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

}
