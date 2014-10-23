package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class AndTransactionFilterTests extends TransactionFilterTestBase {

	private FinancialTransaction transaction;

	@Before
	public void setup() {
		transaction = newTransactionBuilder().date(2014, 1, 1).build();
	}

	@Test
	public void true_and_true_allows() {
		assertTrue(LogicTransactionFilter.and(BooleanTransactionFilter.TRUE,
				BooleanTransactionFilter.TRUE).allow(transaction));
	}

	@Test
	public void true_and_false_allows() {
		assertFalse(LogicTransactionFilter.and(BooleanTransactionFilter.TRUE,
				BooleanTransactionFilter.FALSE).allow(transaction));
	}

	@Test
	public void false_and_true_allows() {
		assertFalse(LogicTransactionFilter.and(BooleanTransactionFilter.FALSE,
				BooleanTransactionFilter.TRUE).allow(transaction));
	}

	@Test
	public void false_and_false_allows() {
		assertFalse(LogicTransactionFilter.and(BooleanTransactionFilter.FALSE,
				BooleanTransactionFilter.FALSE).allow(transaction));
	}

	@Test
	public void short_circuit_rhs_if_lhs_is_false() {
		TransactionFilter mock = mock(TransactionFilter.class);
		assertFalse(LogicTransactionFilter.and(BooleanTransactionFilter.FALSE,
				mock).allow(transaction));
		verify(mock, never()).allow(any(FinancialTransaction.class));
	}

}
