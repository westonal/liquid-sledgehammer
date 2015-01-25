package com.coltsoftware.liquidsledgehammer.collections;

import java.util.ArrayList;
import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class SubTransactionList implements Iterable<SubTransaction> {

	private final ArrayList<SubTransaction> transactions = new ArrayList<SubTransaction>();

	private Money value = Money.Zero;

	public void add(SubTransaction transaction) {
		transactions.add(transaction);
		value = value.add(transaction.getValue());
	}

	public Money getTotalValue() {
		return value;
	}

	@Override
	public Iterator<SubTransaction> iterator() {
		return transactions.iterator();
	}

}
