package com.coltsoftware.liquidsledgehammer.processing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
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
				.value(yen(10000)).groupPattern("holiday").build());
		processor.populateTree(list, root);
		assertEquals(yen(10000), root.getTotalValue());
	}

	@Test
	public void can_populate_tree_with_one_item() {
		resolver.put("holiday", "External.Holiday");
		FinancialTransactionList list = new FinancialTransactionList();
		list.add(new FinancialTransaction.Builder().date(2014, 3, 1)
				.value(euro(10000)).groupPattern("holiday").build());

		processor.populateTree(list, root);

		assertEquals(1, count(root.findOrCreate("External.Holiday")
				.getSubTransactions()));
		assertEquals(euro(10000), root.findOrCreate("External.Holiday")
				.getTotalValue());
		assertEquals(euro(10000), root.getTotalValue());
	}

	@Test
	public void same_transaction_is_in_the_tree() {
		resolver.put("holiday", "External.Holiday");
		FinancialTransactionList list = new FinancialTransactionList();
		FinancialTransaction transaction = new FinancialTransaction.Builder()
				.date(2014, 3, 1).value(yen(10000)).groupPattern("holiday")
				.build();
		list.add(transaction);
		processor.populateTree(list, root);
		assertEquals(transaction, root.findOrCreate("External.Holiday")
				.getSubTransactions().next().getTransaction());
	}

	@Test
	public void can_populate_tree_with_two_items() {
		resolver.put("holiday", "External.Holiday");
		resolver.put("hats", "External.Clothing.Headwear");
		FinancialTransactionList list = new FinancialTransactionList();
		list.add(new FinancialTransaction.Builder().date(2014, 3, 1)
				.value(pounds(10000)).groupPattern("holiday").build());
		list.add(new FinancialTransaction.Builder().date(2014, 4, 1)
				.value(pounds(30000)).groupPattern("hats").build());

		processor.populateTree(list, root);

		assertEquals(1, count(root.findOrCreate("External.Holiday")
				.getSubTransactions()));
		assertEquals(1, count(root.findOrCreate("External.Clothing.Headwear")
				.getSubTransactions()));
		assertEquals(pounds(10000), root.findOrCreate("External.Holiday")
				.getTotalValue());
		assertEquals(pounds(30000),
				root.findOrCreate("External.Clothing.Headwear").getTotalValue());
		assertEquals(pounds(40000), root.getTotalValue());
	}

	@Test
	public void can_populate_tree_with_one_item_with_complex_group() {
		resolver.put("holiday", "External.Holiday");
		resolver.put("hats", "External.Clothing.Headwear");
		FinancialTransactionList list = new FinancialTransactionList();
		list.add(new FinancialTransaction.Builder().date(2014, 3, 1)
				.value(usd(1000)).groupPattern("holiday=3,hats").build());

		processor.populateTree(list, root);

		assertEquals(1, count(root.findOrCreate("External.Holiday")
				.getSubTransactions()));
		assertEquals(1, count(root.findOrCreate("External.Clothing.Headwear")
				.getSubTransactions()));
		assertEquals(usd(300), root.findOrCreate("External.Holiday")
				.getTotalValue());
		assertEquals(usd(700), root.findOrCreate("External.Clothing.Headwear")
				.getTotalValue());
		assertEquals(usd(1000), root.getTotalValue());
	}

}