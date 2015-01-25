package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public enum BooleanTransactionFilter implements TransactionFilter {
	TRUE(true), FALSE(false);

	private final boolean result;

	private BooleanTransactionFilter(boolean result) {
		this.result = result;
	}

	@Override
	public boolean allow(FinancialTransaction transaction) {
		return result;
	}
}
