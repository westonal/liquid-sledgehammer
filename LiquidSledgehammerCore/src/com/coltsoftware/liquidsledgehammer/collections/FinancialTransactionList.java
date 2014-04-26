package com.coltsoftware.liquidsledgehammer.collections;

import java.util.ArrayList;
import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class FinancialTransactionList implements
		FinancialTransactionSource {

	private final ArrayList<FinancialTransaction> transactions = new ArrayList<FinancialTransaction>();

	private Money value = Money.Zero;

	public void add(FinancialTransaction transaction) {
		transactions.add(transaction);
		value = value.add(transaction.getValue());
	}

	public Money getTotalValue() {
		return value;
	}

	@Override
	public Iterator<FinancialTransaction> iterator() {
		return transactions.iterator();
	}

}
