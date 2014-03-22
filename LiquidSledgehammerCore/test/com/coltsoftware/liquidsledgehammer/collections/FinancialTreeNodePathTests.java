package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class FinancialTreeNodePathTests extends
		FinancialTreeNodeTestsBase {

	@Before
	public void setup() {
		root = new FinancialTreeNode();
	}

	@Test
	public void inital_name() {
		assertEquals("", root.getName());
	}

	@Test
	public void inital_parent() {
		assertNull(root.getParent());
	}

	@Test
	public void can_find_empty_path() {
		assertEquals(root, root.findOrCreate(""));
	}

	@Test
	public void can_find_by_short_path() {
		FinancialTreeNode ftn = root.findOrCreate("external");
		assertEquals("external", ftn.getName());
	}

	@Test
	public void parent_set() {
		FinancialTreeNode ftn = root.findOrCreate("external");
		assertEquals(root, ftn.getParent());
	}

	@Test
	public void found_element_exists_on_root() {
		FinancialTreeNode ftn = root.findOrCreate("external");
		ftn.add(createSubTransaction(usd(300)));
		assertEquals(ftn.getTotalValue(), root.getTotalValue());
	}

	@Test
	public void find_element_second_time_returns_same() {
		FinancialTreeNode ftn1 = root.findOrCreate("external");
		FinancialTreeNode ftn2 = root.findOrCreate("external");
		assertSame(ftn1, ftn2);
	}

	@Test
	public void can_find_two_different() {
		FinancialTreeNode ftn1 = root.findOrCreate("external");
		FinancialTreeNode ftn2 = root.findOrCreate("internal");
		assertNotSame(ftn1, ftn2);
	}

	@Test
	public void can_deep_find() {
		FinancialTreeNode ftnOneTwo = root.findOrCreate("one.two");
		FinancialTreeNode ftnOne = root.findOrCreate("one");
		assertEquals(root, ftnOne.getParent());
		assertEquals(ftnOne, ftnOneTwo.getParent());
	}

}
