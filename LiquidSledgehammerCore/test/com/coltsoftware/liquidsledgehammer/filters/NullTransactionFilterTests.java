package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class NullTransactionFilterTests extends TransactionFilterTestBase {

	private NullTransactionFilter filter;

	@Before
	public void setup() {
		filter = NullTransactionFilter.INSTANCE;
	}

	@Test
	public void allows_any_date_hight() {
		FinancialTransaction transaction = newTransactionBuilder().date(2114,
				10, 13).build();
		assertTrue(filter.allow(transaction));
	}

	@Test
	public void allows_any_date_current() {
		FinancialTransaction transaction = newTransactionBuilder().date(2014,
				10, 13).build();
		assertTrue(filter.allow(transaction));
	}

	@Test
	public void allows_any_date_low() {
		FinancialTransaction transaction = newTransactionBuilder().date(1970,
				10, 13).build();
		assertTrue(filter.allow(transaction));
	}

}
