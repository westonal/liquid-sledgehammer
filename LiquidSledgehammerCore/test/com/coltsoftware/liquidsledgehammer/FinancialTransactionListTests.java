package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public final class FinancialTransactionListTests {

	private FinancialTransactionList ftl;

	private static FinancialTransaction makeTransaction(int value) {
		return new FinancialTransaction.Builder().date(2014, 3, 1).value(value)
				.build();
	}

	@Before
	public void setup() {
		ftl = new FinancialTransactionList();
	}

	@Test
	public void inital_total_is_zero() {
		assertEquals(0, ftl.getTotalValue());
	}

	@Test
	public void can_add_transaction() {
		ftl.add(makeTransaction(123));
		assertEquals(123, ftl.getTotalValue());
	}

	@Test
	public void can_add_two_transactions() {
		ftl.add(makeTransaction(123));
		ftl.add(makeTransaction(100));
		assertEquals(223, ftl.getTotalValue());
	}

	@Test
	public void can_loop_single_transaction() {
		FinancialTransaction newTransaction = makeTransaction(123);
		ftl.add(newTransaction);
		int count = 0;
		for (FinancialTransaction transaction : ftl) {
			assertSame(newTransaction, transaction);
			count++;
		}
		assertEquals(1, count);
	}

	@Test
	public void can_loop_two_transactions_in_order() {
		FinancialTransaction newTransaction1 = makeTransaction(100);
		FinancialTransaction newTransaction2 = makeTransaction(200);
		ftl.add(newTransaction1);
		ftl.add(newTransaction2);
		ArrayList<FinancialTransaction> results = new ArrayList<FinancialTransaction>();
		for (FinancialTransaction transaction : ftl)
			results.add(transaction);
		assertEquals(2, results.size());
		assertEquals(newTransaction1, results.get(0));
		assertEquals(newTransaction2, results.get(1));
	}

}
