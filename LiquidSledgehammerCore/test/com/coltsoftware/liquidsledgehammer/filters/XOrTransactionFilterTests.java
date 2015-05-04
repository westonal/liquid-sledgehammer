package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class XOrTransactionFilterTests extends TransactionFilterTestBase {

	private FinancialTransaction transaction;

	@Before
	public void setup() {
		transaction = newTransactionBuilder().date(2014, 1, 1).build();
	}

	@Test
	public void true_and_true_does_not_allow() {
		assertFalse(LogicTransactionFilter.xor(BooleanTransactionFilter.TRUE,
				BooleanTransactionFilter.TRUE).allow(transaction));
	}

	@Test
	public void true_and_false_allows() {
		assertTrue(LogicTransactionFilter.xor(BooleanTransactionFilter.TRUE,
				BooleanTransactionFilter.FALSE).allow(transaction));
	}

	@Test
	public void false_and_true_allows() {
		assertTrue(LogicTransactionFilter.xor(BooleanTransactionFilter.FALSE,
				BooleanTransactionFilter.TRUE).allow(transaction));
	}

	@Test
	public void false_and_false_does_not_allow() {
		assertFalse(LogicTransactionFilter.xor(BooleanTransactionFilter.FALSE,
				BooleanTransactionFilter.FALSE).allow(transaction));
	}

	@Test
	public void perfomance_invocations_are_performed_once_true_true() {
		assertInvokationsArePerformedOnce(true, true);
	}

	@Test
	public void perfomance_invocations_are_performed_once_true_false() {
		assertInvokationsArePerformedOnce(true, false);
	}

	@Test
	public void perfomance_invocations_are_performed_once_false_true() {
		assertInvokationsArePerformedOnce(false, true);
	}

	@Test
	public void perfomance_invocations_are_performed_once_false_false() {
		assertInvokationsArePerformedOnce(false, false);
	}

	private void assertInvokationsArePerformedOnce(boolean lhsValue,
			boolean rhsValue) {
		TransactionFilter rhsMock = mock(TransactionFilter.class);
		TransactionFilter lhsMock = mock(TransactionFilter.class);
		when(lhsMock.allow(any(FinancialTransaction.class))).thenReturn(
				lhsValue);
		when(rhsMock.allow(any(FinancialTransaction.class))).thenReturn(
				rhsValue);
		LogicTransactionFilter.xor(lhsMock, rhsMock).allow(transaction);
		verify(lhsMock, times(1)).allow(any(FinancialTransaction.class));
		verify(rhsMock, times(1)).allow(any(FinancialTransaction.class));
	}
	
	@Test
	public void to_string() {
		TransactionFilter rhsMock = createFilterWithToStringValue("A");
		TransactionFilter lhsMock = createFilterWithToStringValue("B");
		TransactionFilter xor = LogicTransactionFilter.xor(rhsMock, lhsMock);
		assertEquals("(A xor B)", xor.toString());
	}

	@Test
	public void to_string_2() {
		TransactionFilter rhsMock = createFilterWithToStringValue("1");
		TransactionFilter lhsMock = createFilterWithToStringValue("2");
		TransactionFilter xor = LogicTransactionFilter.xor(rhsMock, lhsMock);
		assertEquals("(1 xor 2)", xor.toString());
	}
}
