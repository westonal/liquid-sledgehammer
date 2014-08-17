package com.coltsoftware.liquidsledgehammer.collections;

import org.junit.Before;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.subtransactions.SubTransactionFactory;

public abstract class FinancialTreeNodeTestsBase extends MoneyTestBase {

	protected FinancialTreeNode root;

	protected SubTransaction createSubTransaction(Money value) {
		FinancialTransaction transaction = new FinancialTransaction.Builder()
				.source(NullFinancialTransactionSourceInformation.INSTANCE)
				.date(2014, 5, 1).value(value).build();
		return new SubTransactionFactory().getSubTransactions(transaction)
				.iterator().next();
	}

	@Before
	public void setup() {
		root = new FinancialTreeNode();
	}
}
