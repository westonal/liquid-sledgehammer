package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.BaseTest;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public final class FinancialTreeNodeTests extends BaseTest {

	private FinancialTreeNode ftn;

	private FinancialTransaction createTransaction(long value) {
		return new FinancialTransaction.Builder().date(2014, 5, 1).value(value)
				.build();
	}

	@Before
	public void setup() {
		ftn = new FinancialTreeNode();
	}

	@Test
	public void initial_size() {
		assertEquals(0, count(ftn.getTransactions()));
		assertEquals(0, ftn.getTotalValue().getValue());
	}

	@Test
	public void can_add_transaction() {
		ftn.add(createTransaction(100));
		assertEquals(1, count(ftn.getTransactions()));
		assertEquals(100, ftn.getTotalValue().getValue());
	}

	@Test
	public void can_add_2_transactions() {
		ftn.add(createTransaction(100));
		ftn.add(createTransaction(345));
		assertEquals(2, count(ftn.getTransactions()));
		assertEquals(445, ftn.getTotalValue().getValue());
	}

}
