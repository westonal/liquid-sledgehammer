package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import com.coltsoftware.liquidsledgehammer.androidexample.BasicToStringTagDrawer;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;

public final class FinancialTagDrawer extends BasicToStringTagDrawer {

	@Override
	protected String tagToText(Object tag) {
		if (tag instanceof FinancialTreeNode) {
			FinancialTreeNode node = (FinancialTreeNode) tag;
			return String.format("%s %s", node.getName(), node.getTotalValue());
		}

		if (tag instanceof SubTransaction) {
			SubTransaction subTransaction = (SubTransaction) tag;
			FinancialTransaction transaction = subTransaction.getTransaction();
			return String.format("%s %s %s", transaction.getDate(),
					transaction.getDescription(), subTransaction.getValue());
		}

		return super.tagToText(tag);
	}

}
