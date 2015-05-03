package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class State {

	private final FinancialTransactionSource unfilteredSource;

	public State(FinancialTransactionSource unfilteredSource) {
		this.unfilteredSource = unfilteredSource;
	}

	private FinancialTreeNode currentNode;
	private FinancialTransactionSource source;
	private NodeRecalc nodeRecalc;

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
		if (this.source != source) {
			this.source = source;
			setCurrentNode(nodeRecalc.doRecalc(source));
		}
	}

	public FinancialTransactionSource getUnfilteredSource() {
		return unfilteredSource;
	}

	public void setRecalc(NodeRecalc nodeRecalc) {
		this.nodeRecalc = nodeRecalc;

	}

}
