package com.coltsoftware.liquidsledgehammer.collections;

import org.junit.Before;

import com.coltsoftware.liquidsledgehammer.BaseTest;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public abstract class FinancialTreeNodeTestsBase extends BaseTest {

	protected FinancialTreeNode root;

	protected FinancialTransaction createTransaction(long value) {
		return new FinancialTransaction.Builder().date(2014, 5, 1).value(value)
				.build();
	}

	@Before
	public void setup() {
		root = new FinancialTreeNode();
	}
}
