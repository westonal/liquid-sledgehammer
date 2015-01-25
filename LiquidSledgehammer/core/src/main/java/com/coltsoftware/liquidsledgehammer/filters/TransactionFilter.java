package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public interface TransactionFilter {

	boolean allow(FinancialTransaction transaction);

}
