package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.BaseTest;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class FinancialTreeNodeTests extends BaseTest {

	private FinancialTreeNode root;

	private FinancialTransaction createTransaction(long value) {
		return new FinancialTransaction.Builder().date(2014, 5, 1).value(value)
				.build();
	}

	@Before
	public void setup() {
		root = new FinancialTreeNode();
	}

	@Test
	public void initial_size() {
		assertEquals(0, count(root.getTransactions()));
		assertEquals(0, root.getTotalValue().getValue());
	}

	@Test
	public void can_add_transaction() {
		root.add(createTransaction(100));
		assertEquals(1, count(root.getTransactions()));
		assertEquals(100, root.getTotalValue().getValue());
	}

	@Test
	public void can_add_2_transactions() {
		root.add(createTransaction(100));
		root.add(createTransaction(345));
		assertEquals(2, count(root.getTransactions()));
		assertEquals(445, root.getTotalValue().getValue());
	}
	
	@Test
	public void can_add_another_tree() {
		FinancialTreeNode ftn = new FinancialTreeNode();
		ftn.add(createTransaction(100));
		root.add(ftn);
		assertEquals(0, count(root.getTransactions()));
		assertEquals(100, root.getTotalValue().getValue());
	}
	
	@Test
	public void can_add_two_other_trees() {
		FinancialTreeNode ftn1 = new FinancialTreeNode();
		FinancialTreeNode ftn2 = new FinancialTreeNode();
		ftn1.add(createTransaction(100));
		ftn2.add(createTransaction(200));
		root.add(ftn1);
		root.add(ftn2);
		assertEquals(0, count(root.getTransactions()));
		assertEquals(300, root.getTotalValue().getValue());
	}

}
