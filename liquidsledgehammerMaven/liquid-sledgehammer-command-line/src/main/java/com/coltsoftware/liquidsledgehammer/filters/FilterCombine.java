package com.coltsoftware.liquidsledgehammer.filters;

import java.util.ArrayList;

public final class FilterCombine {
	private FilterCombine() {
	}

	public static TransactionFilter andAll(ArrayList<TransactionFilter> filters) {
		if (filters.isEmpty())
			return BooleanTransactionFilter.TRUE;
		TransactionFilter composite = filters.get(filters.size() - 1);
		for (int i = filters.size() - 2; i >= 0; i--)
			composite = LogicTransactionFilter.and(filters.get(i), composite);
		return composite;
	}

	public static TransactionFilter orAll(ArrayList<TransactionFilter> filters) {
		if (filters.isEmpty())
			return BooleanTransactionFilter.TRUE;
		TransactionFilter composite = filters.get(filters.size() - 1);
		for (int i = filters.size() - 2; i >= 0; i--)
			composite = LogicTransactionFilter.or(filters.get(i), composite);
		return composite;
	}

}
