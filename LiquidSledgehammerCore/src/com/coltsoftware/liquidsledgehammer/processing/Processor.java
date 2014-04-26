package com.coltsoftware.liquidsledgehammer.processing;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class Processor {

	private final AliasPathResolver resolver;

	public Processor(AliasPathResolver resolver) {
		this.resolver = resolver;
	}

	public void populateTree(FinancialTransactionSource list,
			FinancialTreeNode root) {
		for (FinancialTransaction transaction : list)
			addTransaction(root, transaction);
	}

	private void addTransaction(FinancialTreeNode root,
			FinancialTransaction transaction) {
		for (SubTransaction subTransaction : transaction.getSubTransactions())
			root.findOrCreate(resolver.resolve(subTransaction.getGroup())).add(
					subTransaction);
	}

}
