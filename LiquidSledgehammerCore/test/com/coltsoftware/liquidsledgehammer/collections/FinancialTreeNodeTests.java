package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public final class FinancialTreeNodeTests extends FinancialTreeNodeTestsBase {

	@Test
	public void initial_size() {
		assertEquals(0, count(root.getSubTransactions()));
		assertTrue(root.getTotalValue().isZero());
	}

	@Test
	public void can_add_transaction() {
		root.add(createSubTransaction(usd(100)));
		assertEquals(1, count(root.getSubTransactions()));
		assertEquals(usd(100), root.getTotalValue());
	}

	@Test
	public void can_add_2_transactions() {
		root.add(createSubTransaction(usd(100)));
		root.add(createSubTransaction(usd(345)));
		assertEquals(2, count(root.getSubTransactions()));
		assertEquals(usd(445), root.getTotalValue());
	}

	@Test
	public void can_add_another_tree() {
		FinancialTreeNode ftn = new FinancialTreeNode();
		ftn.add(createSubTransaction(pounds(100)));
		root.add(ftn);
		assertEquals(0, count(root.getSubTransactions()));
		assertEquals(pounds(100), root.getTotalValue());
	}

	@Test
	public void can_add_two_other_trees() {
		FinancialTreeNode ftn1 = new FinancialTreeNode();
		FinancialTreeNode ftn2 = new FinancialTreeNode();
		ftn1.add(createSubTransaction(yen(100)));
		ftn2.add(createSubTransaction(yen(200)));
		root.add(ftn1);
		root.add(ftn2);
		assertEquals(0, count(root.getSubTransactions()));
		assertEquals(yen(300), root.getTotalValue());
	}

	@Test
	public void parent_after_add_tree() {
		FinancialTreeNode ftn = new FinancialTreeNode();
		root.add(ftn);
		assertEquals(root, ftn.getParent());
	}

	@Test(expected = TreeException.class)
	public void cant_assign_to_two_parents() {
		FinancialTreeNode otherRoot = new FinancialTreeNode();
		FinancialTreeNode ftn = new FinancialTreeNode();
		root.add(ftn);
		otherRoot.add(ftn);
	}

}
