package com.coltsoftware.liquidsledgehammer.processing;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;
import com.coltsoftware.liquidsledgehammer.subtransactions.SubTransactionFactory;

public final class Processor {

	private final AliasPathResolver resolver;
	private final SubTransactionFactory subTransactionFactory;

	public Processor(AliasPathResolver resolver,
			SubTransactionFactory subTransactionFactory) {
		this.resolver = resolver;
		this.subTransactionFactory = subTransactionFactory;
	}

	public void populateTree(FinancialTransactionSource list,
			FinancialTreeNode root) {
		for (FinancialTransaction transaction : list)
			addTransaction(root, transaction);
	}

	private void addTransaction(FinancialTreeNode root,
			FinancialTransaction transaction) {
		Iterable<SubTransaction> subTransactions = subTransactionFactory
				.getSubTransactions(transaction);
		for (SubTransaction subTransaction : subTransactions)
			root.findOrCreate(resolver.resolve(subTransaction.getGroup())).add(
					subTransaction);
	}

}
