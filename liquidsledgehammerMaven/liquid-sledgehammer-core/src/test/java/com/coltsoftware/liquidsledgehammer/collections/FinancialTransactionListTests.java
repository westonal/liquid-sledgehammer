package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;

public final class FinancialTransactionListTests extends MoneyTestBase {

	private FinancialTransactionList ftl;

	private static FinancialTransaction makeTransaction(Money value) {
		return new FinancialTransaction.Builder()
				.source(NullFinancialTransactionSourceInformation.INSTANCE)
				.date(2014, 3, 1).value(value).build();
	}

	@Before
	public void setup() {
		ftl = new FinancialTransactionList();
	}

	@Test
	public void inital_total_is_zero() {
		assertTrue(ftl.getTotalValue().isZero());
	}

	@Test
	public void can_add_transaction() {
		ftl.add(makeTransaction(gbp(123)));
		assertEquals(gbp(123), ftl.getTotalValue());
	}

	@Test
	public void can_add_two_transactions() {
		ftl.add(makeTransaction(usd(123)));
		ftl.add(makeTransaction(usd(100)));
		assertEquals(usd(223), ftl.getTotalValue());
	}

	@Test
	public void can_loop_single_transaction() {
		FinancialTransaction newTransaction = makeTransaction(yen(123));
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
		FinancialTransaction newTransaction1 = makeTransaction(yen(100));
		FinancialTransaction newTransaction2 = makeTransaction(yen(200));
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
