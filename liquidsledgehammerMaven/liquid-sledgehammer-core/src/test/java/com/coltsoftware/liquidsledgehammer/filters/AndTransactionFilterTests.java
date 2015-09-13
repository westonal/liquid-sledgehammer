package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class AndTransactionFilterTests extends TransactionFilterTestBase {

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
		assertTrue(LogicTransactionFilter.and(t, t).allow(transaction));
	}

	@Test
	public void true_and_false_does_not_allow() {
		assertFalse(LogicTransactionFilter.and(t, f).allow(transaction));
	}

	@Test
	public void false_and_true_does_not_allow() {
		assertFalse(LogicTransactionFilter.and(f, t).allow(transaction));
	}

	@Test
	public void false_and_false_does_not_allow() {
		assertFalse(LogicTransactionFilter.and(f, f).allow(transaction));
	}

	@Test
	public void short_circuit_rhs_if_lhs_is_false() {
		TransactionFilter rhsMock = mock(TransactionFilter.class);
		assertFalse(LogicTransactionFilter.and(f, rhsMock).allow(transaction));
		verify(rhsMock, never()).allow(any(FinancialTransaction.class));
	}

	@Test
	public void to_string() {
		TransactionFilter rhsMock = createFilterWithToStringValue("A");
		TransactionFilter lhsMock = createFilterWithToStringValue("B");
		TransactionFilter and = LogicTransactionFilter.and(rhsMock, lhsMock);
		assertEquals("A and B", and.toString());
	}

	@Test
	public void to_string_2() {
		TransactionFilter rhsMock = createFilterWithToStringValue("1");
		TransactionFilter lhsMock = createFilterWithToStringValue("2");
		TransactionFilter and = LogicTransactionFilter.and(rhsMock, lhsMock);
		assertEquals("1 and 2", and.toString());
	}

	@Test
	public void short_cutting_if_lhs_boolean_true_constant_return_rhs() {
		assertSame(example, LogicTransactionFilter.and(
				BooleanTransactionFilter.TRUE, example));
	}

	@Test
	public void short_cutting_if_rhs_boolean_true_constant_return_lhs() {
		assertSame(example, LogicTransactionFilter.and(example,
				BooleanTransactionFilter.TRUE));
	}

	@Test
	public void short_cutting_if_lhs_boolean_false_constant_return_false() {
		assertSame(BooleanTransactionFilter.FALSE, LogicTransactionFilter.and(
				BooleanTransactionFilter.FALSE, example));
	}

	@Test
	public void short_cutting_if_rhs_boolean_false_constant_return_false() {
		assertSame(BooleanTransactionFilter.FALSE, LogicTransactionFilter.and(
				example, BooleanTransactionFilter.FALSE));
	}

}
