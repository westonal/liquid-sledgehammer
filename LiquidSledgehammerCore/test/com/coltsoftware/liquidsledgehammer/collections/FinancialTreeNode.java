package com.coltsoftware.liquidsledgehammer.collections;

import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class FinancialTreeNode {

	private final FinancialTransactionList transactions = new FinancialTransactionList();

	public void add(FinancialTransaction transaction) {
		transactions.add(transaction);
	}

	public Iterator<FinancialTransaction> getTransactions() {
		return transactions.iterator();
	}

	public Money getTotalValue() {
		return transactions.getTotalValue();
	}
}
