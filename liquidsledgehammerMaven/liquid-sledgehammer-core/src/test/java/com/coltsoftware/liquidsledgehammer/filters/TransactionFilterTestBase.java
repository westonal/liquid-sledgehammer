package com.coltsoftware.liquidsledgehammer.filters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;

public abstract class TransactionFilterTestBase extends MoneyTestBase {

	protected static Builder newTransactionBuilder() {
		return new FinancialTransaction.Builder()
				.source(NullFinancialTransactionSourceInformation.INSTANCE);
	}

	protected static TransactionFilter createFilterWithToStringValue(
			String toStringValue) {
		TransactionFilter mock = mock(TransactionFilter.class);
		when(mock.toString()).thenReturn(toStringValue);
		return mock;
	}

}