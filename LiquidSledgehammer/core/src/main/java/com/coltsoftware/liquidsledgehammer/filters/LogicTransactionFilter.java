package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class LogicTransactionFilter {

	private LogicTransactionFilter() {
	}

	public static TransactionFilter and(TransactionFilter lhsFilter,
			TransactionFilter rhsFilter) {
		return new AndTransactionFilter(lhsFilter, rhsFilter);
	}

	public static TransactionFilter or(TransactionFilter lhsFilter,
			TransactionFilter rhsFilter) {
		return new OrTransactionFilter(lhsFilter, rhsFilter);
	}

	public static TransactionFilter not(TransactionFilter filter) {
		if (filter instanceof NotTransactionFilter)
			return ((NotTransactionFilter) filter).getInnerFilter();
		return new NotTransactionFilter(filter);
	}

	public static TransactionFilter xor(TransactionFilter lhsFilter,
			TransactionFilter rhsFilter) {
		return new XOrTransactionFilter(lhsFilter, rhsFilter);
	}

	private static abstract class BinaryOperatorTransactionFilter implements
			TransactionFilter {

		protected final TransactionFilter lhsFilter;
		protected final TransactionFilter rhsFilter;

		public BinaryOperatorTransactionFilter(TransactionFilter lhsFilter,
				TransactionFilter rhsFilter) {
			this.lhsFilter = lhsFilter;
			this.rhsFilter = rhsFilter;
		}
	}

	private static class AndTransactionFilter extends
			BinaryOperatorTransactionFilter {

		public AndTransactionFilter(TransactionFilter lhsFilter,
				TransactionFilter rhsFilter) {
			super(lhsFilter, rhsFilter);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return lhsFilter.allow(transaction) && rhsFilter.allow(transaction);
		}
	}

	private static class OrTransactionFilter extends
			BinaryOperatorTransactionFilter {

		public OrTransactionFilter(TransactionFilter lhsFilter,
				TransactionFilter rhsFilter) {
			super(lhsFilter, rhsFilter);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return lhsFilter.allow(transaction) || rhsFilter.allow(transaction);
		}
	}

	private static class XOrTransactionFilter extends
			BinaryOperatorTransactionFilter {

		public XOrTransactionFilter(TransactionFilter lhsFilter,
				TransactionFilter rhsFilter) {
			super(lhsFilter, rhsFilter);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return lhsFilter.allow(transaction) ^ rhsFilter.allow(transaction);
		}
	}

	private static class NotTransactionFilter implements TransactionFilter {

		protected final TransactionFilter innerFilter;

		public NotTransactionFilter(TransactionFilter filter) {
			this.innerFilter = filter;
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return !innerFilter.allow(transaction);
		}

		public TransactionFilter getInnerFilter() {
			return innerFilter;
		}
	}
}
