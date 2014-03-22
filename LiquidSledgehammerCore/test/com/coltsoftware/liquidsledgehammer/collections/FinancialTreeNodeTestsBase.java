package com.coltsoftware.liquidsledgehammer.collections;

import org.junit.Before;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;

public abstract class FinancialTreeNodeTestsBase extends MoneyTestBase {

	protected FinancialTreeNode root;

	protected SubTransaction createSubTransaction(Money value) {
		return new FinancialTransaction.Builder().date(2014, 5, 1).value(value)
				.build().getSubTransactions().iterator().next();
	}

	@Before
	public void setup() {
		root = new FinancialTreeNode();
	}
}
