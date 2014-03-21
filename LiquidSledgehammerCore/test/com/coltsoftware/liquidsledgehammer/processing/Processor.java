package com.coltsoftware.liquidsledgehammer.processing;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class Processor {

	private final AliasPathResolver resolver;

	public Processor(AliasPathResolver resolver) {
		this.resolver = resolver;
	}

	public void populateTree(FinancialTransactionList list,
			FinancialTreeNode root) {
		for (FinancialTransaction transaction : list)
			root.findOrCreate(resolver.resolve(transaction.getGroupPattern())).add(transaction);
	}

}
