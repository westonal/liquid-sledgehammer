package com.coltsoftware.liquidsledgehammer.collections;

import java.util.ArrayList;
import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class FinancialTreeNode {

	private final FinancialTransactionList transactions = new FinancialTransactionList();
	private final ArrayList<FinancialTreeNode> children = new ArrayList<FinancialTreeNode>();

	public void add(FinancialTransaction transaction) {
		transactions.add(transaction);
	}

	public void add(FinancialTreeNode child) {
		children.add(child);
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

}
