package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;

public final class FinancialTreeNodeRemovalTests extends
		FinancialTreeNodeTestsBase {

	private FinancialTreeNode ftn1;
	private FinancialTreeNode ftn2;
	private FinancialTreeNode ftn22;

	@Before
	public void setup() {
		super.setup();
		ftn1 = new FinancialTreeNode();
		ftn2 = new FinancialTreeNode();
		ftn22 = new FinancialTreeNode();
		ftn1.add(createSubTransaction(yen(100)));
		ftn2.add(createSubTransaction(yen(200)));
		ftn22.add(createSubTransaction(yen(30)));
		ftn2.add(ftn22);
		root.add(ftn1);
		root.add(ftn2);
		assertEquals(yen(330), root.getTotalValue());
	}

	@Test
	public void null_parent_after_removed() {
		ftn1.remove();
		assertNull(ftn1.getParent());
	}

	@Test
	public void root_does_not_have_as_child_any_longer() {
		ftn1.remove();
		assertEquals(1, count(root));
	}

	@Test
	public void roots_value_is_adjusted() {
		ftn1.remove();
		assertEquals(yen(230), root.getTotalValue());
	}

	@Test
	public void when_sub_tree_node_is_removed_roots_value_is_adjusted() {
		ftn22.remove();
		assertEquals(yen(300), root.getTotalValue());
	}

	@Test
	public void when_sub_tree_node_is_removed_middle_nodes_value_is_adjusted() {
		ftn22.remove();
		assertEquals(yen(200), ftn2.getTotalValue());
	}
}
