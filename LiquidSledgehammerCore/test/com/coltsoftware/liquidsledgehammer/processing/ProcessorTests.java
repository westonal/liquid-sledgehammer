package com.coltsoftware.liquidsledgehammer.processing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class ProcessorTests extends MoneyTestBase {

	private Processor processor;
	private AliasPathResolver resolver;
	private FinancialTreeNode root;

	@Before
	public void setup() {
		resolver = new AliasPathResolver();
		processor = new Processor(resolver);
		root = new FinancialTreeNode();
	}

	@Test
	public void can_populate_tree_with_one_item_without_resolution() {
		FinancialTransactionList list = new FinancialTransactionList();
		list.add(new FinancialTransaction.Builder().date(2014, 3, 1)
				.value(10000).groupPattern("holiday").build());
		processor.populateTree(list, root);
		assertEquals(10000, root.getTotalValue().getValue());
	}

	@Test
	public void can_populate_tree_with_one_item() {
		resolver.put("holiday", "External.Holiday");
		FinancialTransactionList list = new FinancialTransactionList();
		list.add(new FinancialTransaction.Builder().date(2014, 3, 1)
				.value(10000).groupPattern("holiday").build());

		processor.populateTree(list, root);

		assertEquals(1, count(root.findOrCreate("External.Holiday")
				.getTransactions()));
		assertEquals(10000, root.findOrCreate("External.Holiday")
				.getTotalValue().getValue());
		assertEquals(10000, root.getTotalValue().getValue());
	}

	@Test
	public void same_transaction_is_in_the_tree() {
		resolver.put("holiday", "External.Holiday");
		FinancialTransactionList list = new FinancialTransactionList();
		FinancialTransaction transaction = new FinancialTransaction.Builder()
				.date(2014, 3, 1).value(10000).groupPattern("holiday").build();
		list.add(transaction);
		processor.populateTree(list, root);
		assertEquals(transaction, root.findOrCreate("External.Holiday")
				.getTransactions().next());
	}

	@Test
	public void can_populate_tree_with_two_items() {
		resolver.put("holiday", "External.Holiday");
		resolver.put("hats", "External.Clothing.Headwear");
		FinancialTransactionList list = new FinancialTransactionList();
		list.add(new FinancialTransaction.Builder().date(2014, 3, 1)
				.value(10000).groupPattern("holiday").build());
		list.add(new FinancialTransaction.Builder().date(2014, 4, 1)
				.value(30000).groupPattern("hats").build());

		processor.populateTree(list, root);

		assertEquals(1, count(root.findOrCreate("External.Holiday")
				.getTransactions()));
		assertEquals(1, count(root.findOrCreate("External.Clothing.Headwear")
				.getTransactions()));
		assertEquals(10000, root.findOrCreate("External.Holiday")
				.getTotalValue().getValue());
		assertEquals(30000, root.findOrCreate("External.Clothing.Headwear")
				.getTotalValue().getValue());
		assertEquals(40000, root.getTotalValue().getValue());
	}

	@Test
	@Ignore
	public void can_populate_tree_with_one_item_with_complex_group() {
		resolver.put("holiday", "External.Holiday");
		resolver.put("hats", "External.Clothing.Headwear");
		FinancialTransactionList list = new FinancialTransactionList();
		list.add(new FinancialTransaction.Builder().date(2014, 3, 1)
				.value(10000).groupPattern("holiday=3,hats").build());

		processor.populateTree(list, root);

		assertEquals(1, count(root.findOrCreate("External.Holiday")
				.getTransactions()));
		assertEquals(1, count(root.findOrCreate("External.Clothing.Headwear")
				.getTransactions()));
		assertEquals(300, root.findOrCreate("External.Holiday").getTotalValue()
				.getValue());
		assertEquals(700, root.findOrCreate("External.Clothing.Headwear")
				.getTotalValue().getValue());
		assertEquals(10000, root.getTotalValue().getValue());
	}

}
