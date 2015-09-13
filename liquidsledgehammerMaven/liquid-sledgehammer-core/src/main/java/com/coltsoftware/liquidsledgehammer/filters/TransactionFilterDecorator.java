package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

abstract class TransactionFilterDecorator implements TransactionFilter {

	private final TransactionFilter decorated;

	public TransactionFilterDecorator(TransactionFilter decorated) {
		this.decorated = decorated;
	}

	@Override
	public boolean allow(FinancialTransaction transaction) {
		return decorated.allow(transaction);
	}
}
