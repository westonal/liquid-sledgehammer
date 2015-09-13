package com.coltsoftware.liquidsledgehammer;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.filters.FilterCombine;
import com.coltsoftware.liquidsledgehammer.filters.TransactionFilter;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class State {

	private final FinancialTransactionSource unfilteredSource;
	private final TransactionFilter topLevelFilter;
	private final ArrayList<TransactionFilter> userFilters = new ArrayList<TransactionFilter>();

	public State(FinancialTransactionSource unfilteredSource,
			TransactionFilter topLevelFilter) {
		this.unfilteredSource = unfilteredSource;
		this.topLevelFilter = topLevelFilter;
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

	public void setLatestFilter(TransactionFilter filter) {
		userFilters.add(filter);
	}

	public void setRecalc(NodeRecalc nodeRecalc) {
		this.nodeRecalc = nodeRecalc;
	}

	public void clearFilters() {
		userFilters.clear();
	}

	public TransactionFilter getFilter() {
		userFilters.add(0, topLevelFilter);
		TransactionFilter result = FilterCombine.andAll(userFilters);
		userFilters.remove(0);
		return result;
	}

}
