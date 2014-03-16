package com.coltsoftware.liquidsledgehammer;

import java.util.ArrayList;
import java.util.Iterator;

public final class FinancialTransactionList implements
		Iterable<FinancialTransaction> {

	private final ArrayList<FinancialTransaction> transactions = new ArrayList<FinancialTransaction>();

	private long value;

	public void add(FinancialTransaction transaction) {
		transactions.add(transaction);
		value += transaction.getValue();
	}

	public long getTotalValue() {
		return value;
	}

	@Override
	public Iterator<FinancialTransaction> iterator() {
		return transactions.iterator();
	}

}
