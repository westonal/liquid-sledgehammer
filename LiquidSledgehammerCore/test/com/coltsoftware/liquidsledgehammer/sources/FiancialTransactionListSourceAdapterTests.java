package com.coltsoftware.liquidsledgehammer.sources;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.BaseTest;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class FiancialTransactionListSourceAdapterTests extends BaseTest {

	private FinancialTransactionList financialTransactionList;
	private FinancialTransactionSource fiancialTransactionListSourceAdapter;

	private static FinancialTransaction makeTransaction(int value) {
		return new FinancialTransaction.Builder().date(2014, 3, 1).value(value)
				.build();
	}

	@Before
	public void setup() {
		financialTransactionList = new FinancialTransactionList();
		fiancialTransactionListSourceAdapter = new FinancialTransactionListSourceAdapter(
				financialTransactionList);
	}

	@Test
	public void initial_count() {
		assertEquals(0, count(fiancialTransactionListSourceAdapter));
	}

	@Test
	public void can_add_to_underlying_list_and_adapter_shows() {
		FinancialTransaction transaction = makeTransaction(100);
		financialTransactionList.add(transaction);
		assertEquals(1, count(fiancialTransactionListSourceAdapter));
		assertEquals(transaction, fiancialTransactionListSourceAdapter
				.iterator().next());
	}

	@Test
	public void can_add_two_to_underlying_list_and_adapter_shows() {
		FinancialTransaction transaction1 = makeTransaction(100);
		FinancialTransaction transaction2 = makeTransaction(200);
		financialTransactionList.add(transaction1);
		financialTransactionList.add(transaction2);
		assertEquals(2, count(fiancialTransactionListSourceAdapter));
		Iterator<FinancialTransaction> iterator = fiancialTransactionListSourceAdapter
				.iterator();
		assertEquals(transaction1, iterator.next());
		assertEquals(transaction2, iterator.next());
	}

}
