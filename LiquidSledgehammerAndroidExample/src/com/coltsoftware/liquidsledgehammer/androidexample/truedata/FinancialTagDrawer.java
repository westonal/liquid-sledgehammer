package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import com.coltsoftware.liquidsledgehammer.androidexample.BasicToStringTagDrawer;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;

public final class FinancialTagDrawer extends BasicToStringTagDrawer {

	@Override
	protected String tagToText(Object tag) {
		if (tag instanceof FinancialTreeNode) {
			FinancialTreeNode node = (FinancialTreeNode) tag;
			return String.format("%s %s", node.getName(), node.getTotalValue());
		} else
			return super.tagToText(tag);
	}

}
