package com.coltsoftware.liquidsledgehammer.sources;

import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class FinancialTransactionListSourceAdapter implements
		FinancialTransactionSource {

	private final FinancialTransactionList financialTransactionList;

	public FinancialTransactionListSourceAdapter(
			FinancialTransactionList financialTransactionList) {
		this.financialTransactionList = financialTransactionList;
	}

	@Override
	public Iterator<FinancialTransaction> iterator() {
		return financialTransactionList.iterator();
	}

}
