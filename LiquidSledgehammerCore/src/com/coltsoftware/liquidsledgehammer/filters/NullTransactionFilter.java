package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public enum NullTransactionFilter implements TransactionFilter {

	INSTANCE;

	@Override
	public boolean allow(FinancialTransaction transaction) {
		return true;
	}
}
