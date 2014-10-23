package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class OrTransactionFilterTests extends TransactionFilterTestBase {

	private FinancialTransaction transaction;

	@Before
	public void setup() {
		transaction = newTransactionBuilder().date(2014, 1, 1).build();
	}

	@Test
	public void true_and_true_allows() {
		assertTrue(LogicTransactionFilter.or(BooleanTransactionFilter.TRUE,
				BooleanTransactionFilter.TRUE).allow(transaction));
	}

	@Test
	public void true_and_false_allows() {
		assertTrue(LogicTransactionFilter.or(BooleanTransactionFilter.TRUE,
				BooleanTransactionFilter.FALSE).allow(transaction));
	}

	@Test
	public void false_and_true_allows() {
		assertTrue(LogicTransactionFilter.or(BooleanTransactionFilter.FALSE,
				BooleanTransactionFilter.TRUE).allow(transaction));
	}

	@Test
	public void false_and_false_does_not_allow() {
		assertFalse(LogicTransactionFilter.or(BooleanTransactionFilter.FALSE,
				BooleanTransactionFilter.FALSE).allow(transaction));
	}

	@Test
	public void short_circuit_rhs_if_lhs_is_true() {
		TransactionFilter rhsMock = mock(TransactionFilter.class);
		assertTrue(LogicTransactionFilter.or(BooleanTransactionFilter.TRUE,
				rhsMock).allow(transaction));
		verify(rhsMock, never()).allow(any(FinancialTransaction.class));
	}

}
