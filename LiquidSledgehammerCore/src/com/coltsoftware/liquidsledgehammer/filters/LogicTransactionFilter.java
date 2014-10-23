package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class LogicTransactionFilter {

	private LogicTransactionFilter() {
	}

	public static TransactionFilter and(TransactionFilter lhsFilter,
			TransactionFilter rhsFilter) {
		return new AndTransactionFilter(lhsFilter, rhsFilter);
	}

	private static abstract class BooleanOperatorTransactionFilter implements
			TransactionFilter {

		protected final TransactionFilter lhsFilter;
		protected final TransactionFilter rhsFilter;

		public BooleanOperatorTransactionFilter(TransactionFilter lhsFilter,
				TransactionFilter rhsFilter) {
			this.lhsFilter = lhsFilter;
			this.rhsFilter = rhsFilter;
		}

	}

	private static class AndTransactionFilter extends
			BooleanOperatorTransactionFilter {

		public AndTransactionFilter(TransactionFilter lhsFilter,
				TransactionFilter rhsFilter) {
			super(lhsFilter, rhsFilter);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return lhsFilter.allow(transaction) && rhsFilter.allow(transaction);
		}
	}

}
