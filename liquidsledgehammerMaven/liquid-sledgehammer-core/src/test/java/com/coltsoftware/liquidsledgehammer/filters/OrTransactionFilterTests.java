package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class OrTransactionFilterTests extends TransactionFilterTestBase {

	private FinancialTransaction transaction;
	private TransactionFilter t;
	private TransactionFilter f;
	private TransactionFilter example;

	@Before
	public void setup() {
		transaction = newTransactionBuilder().date(2014, 1, 1).build();
		t = new TransactionFilter() {
			@Override
			public boolean allow(FinancialTransaction transaction) {
				return true;
			}
		};
		f = new TransactionFilter() {
			@Override
			public boolean allow(FinancialTransaction transaction) {
				return false;
			}
		};
		example = new TransactionFilter() {
			@Override
			public boolean allow(FinancialTransaction transaction) {
				return false;
			}
		};
	}

	@Test
	public void true_and_true_allows() {
		assertTrue(LogicTransactionFilter.or(t, t).allow(transaction));
	}

	@Test
	public void true_and_false_allows() {
		assertTrue(LogicTransactionFilter.or(t, f).allow(transaction));
	}

	@Test
	public void false_and_true_allows() {
		assertTrue(LogicTransactionFilter.or(f, t).allow(transaction));
	}

	@Test
	public void false_and_false_does_not_allow() {
		assertFalse(LogicTransactionFilter.or(f, f).allow(transaction));
	}

	@Test
	public void short_circuit_rhs_if_lhs_is_true() {
		TransactionFilter rhsMock = mock(TransactionFilter.class);
		assertTrue(LogicTransactionFilter.or(t, rhsMock).allow(transaction));
		verify(rhsMock, never()).allow(any(FinancialTransaction.class));
	}

	@Test
	public void to_string() {
		TransactionFilter rhsMock = createFilterWithToStringValue("A");
		TransactionFilter lhsMock = createFilterWithToStringValue("B");
		TransactionFilter or = LogicTransactionFilter.or(rhsMock, lhsMock);
		assertEquals("(A or B)", or.toString());
	}

	@Test
	public void to_string_2() {
		TransactionFilter rhsMock = createFilterWithToStringValue("1");
		TransactionFilter lhsMock = createFilterWithToStringValue("2");
		TransactionFilter or = LogicTransactionFilter.or(rhsMock, lhsMock);
		assertEquals("(1 or 2)", or.toString());
	}

	@Test
	public void short_cutting_if_lhs_boolean_true_constant_return_true() {
		assertSame(BooleanTransactionFilter.TRUE, LogicTransactionFilter.or(
				BooleanTransactionFilter.TRUE, example));
	}

	@Test
	public void short_cutting_if_rhs_boolean_true_constant_return_true() {
		assertSame(BooleanTransactionFilter.TRUE, LogicTransactionFilter.or(
				example, BooleanTransactionFilter.TRUE));
	}

	@Test
	public void short_cutting_if_lhs_boolean_false_constant_return_rhs() {
		assertSame(example, LogicTransactionFilter.or(
				BooleanTransactionFilter.FALSE, example));
	}

	@Test
	public void short_cutting_if_rhs_boolean_false_constant_return_lhs() {
		assertSame(example, LogicTransactionFilter.or(example,
				BooleanTransactionFilter.FALSE));
	}

}
