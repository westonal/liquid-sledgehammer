package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class BooleanTransactionFilterTests extends
		TransactionFilterTestBase {

	private FinancialTransaction transaction;

	@Before
	public void setup() {
		transaction = newTransactionBuilder().date(2014, 1, 1).build();
	}

	@Test
	public void false_blocks_all() {
		assertFalse(BooleanTransactionFilter.FALSE.allow(transaction));
	}

	@Test
	public void true_allows_all() {
		assertTrue(BooleanTransactionFilter.TRUE.allow(transaction));
	}

}
