package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class NotTransactionFilterTests extends TransactionFilterTestBase {

	private FinancialTransaction transaction;

	@Before
	public void setup() {
		transaction = newTransactionBuilder().date(2014, 1, 1).build();
	}

	@Test
	public void not_true_does_not_allow() {
		assertFalse(LogicTransactionFilter.not(BooleanTransactionFilter.TRUE)
				.allow(transaction));
	}

	@Test
	public void not_false_allows() {
		assertTrue(LogicTransactionFilter.not(BooleanTransactionFilter.FALSE)
				.allow(transaction));
	}

	@Test
	public void not_of_not_removes_not() {
		TransactionFilter mock = mock(TransactionFilter.class);
		TransactionFilter filter = LogicTransactionFilter.not(mock);
		assertNotSame(mock, filter);
		TransactionFilter doubleNegativeFilter = LogicTransactionFilter
				.not(filter);
		assertSame(mock, doubleNegativeFilter);
	}

	@Test
	public void to_string() {
		TransactionFilter mock = createFilterWithToStringValue("A");
		TransactionFilter or = LogicTransactionFilter.not(mock);
		assertEquals("not(A)", or.toString());
	}

	@Test
	public void to_string_2() {
		TransactionFilter mock = createFilterWithToStringValue("1");
		TransactionFilter or = LogicTransactionFilter.not(mock);
		assertEquals("not(1)", or.toString());
	}
}
