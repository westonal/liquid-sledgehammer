package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.collections.SubTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.subtransactions.SubTransactionFactory;

public final class SubTransactionListTests extends MoneyTestBase {

	private SubTransactionList ftl;

	private static SubTransaction makeSubTransaction(Money value) {
		FinancialTransaction transaction = new FinancialTransaction.Builder()
				.date(2014, 3, 1).value(value).build();
		return new SubTransactionFactory().getSubTransactions(transaction)
				.iterator().next();
	}

	@Before
	public void setup() {
		ftl = new SubTransactionList();
	}

	@Test
	public void inital_total_is_zero() {
		assertTrue(ftl.getTotalValue().isZero());
	}

	@Test
	public void can_add_transaction() {
		ftl.add(makeSubTransaction(gbp(123)));
		assertEquals(gbp(123), ftl.getTotalValue());
	}

	@Test
	public void can_add_two_transactions() {
		ftl.add(makeSubTransaction(usd(123)));
		ftl.add(makeSubTransaction(usd(100)));
		assertEquals(usd(223), ftl.getTotalValue());
	}

	@Test
	public void can_loop_single_transaction() {
		SubTransaction newTransaction = makeSubTransaction(yen(123));
		ftl.add(newTransaction);
		int count = 0;
		for (SubTransaction transaction : ftl) {
			assertSame(newTransaction, transaction);
			count++;
		}
		assertEquals(1, count);
	}

	@Test
	public void can_loop_two_transactions_in_order() {
		SubTransaction newTransaction1 = makeSubTransaction(yen(100));
		SubTransaction newTransaction2 = makeSubTransaction(yen(200));
		ftl.add(newTransaction1);
		ftl.add(newTransaction2);
		ArrayList<SubTransaction> results = new ArrayList<SubTransaction>();
		for (SubTransaction transaction : ftl)
			results.add(transaction);
		assertEquals(2, results.size());
		assertEquals(newTransaction1, results.get(0));
		assertEquals(newTransaction2, results.get(1));
	}

}
