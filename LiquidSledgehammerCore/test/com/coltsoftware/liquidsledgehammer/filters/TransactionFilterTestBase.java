package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;

public abstract class TransactionFilterTestBase extends MoneyTestBase {

	protected static Builder newTransactionBuilder() {
		return new FinancialTransaction.Builder()
				.source(NullFinancialTransactionSourceInformation.INSTANCE);
	}

	public TransactionFilterTestBase() {
		super();
	}

}