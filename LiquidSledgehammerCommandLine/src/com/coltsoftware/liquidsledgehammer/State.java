package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class State {
	private FinancialTreeNode currentNode;
	private FinancialTransactionSource source;

	public FinancialTreeNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(FinancialTreeNode root) {
		this.currentNode = root;
	}

	public FinancialTransactionSource getSource() {
		return source;
	}

	public void setSource(FinancialTransactionSource source) {
		this.source = source;
	}

}
