package com.coltsoftware.liquidsledgehammer.filters;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class TransactionSourceFilter {

	private TransactionSourceFilter() {
	}

	public static class Builder {

		private String name;
		private boolean insensitive;

		public TransactionFilter build() {
			if (name == null)
				return BooleanTransactionFilter.TRUE;
			if (insensitive)
				return new CaseInsensitiveTransactionSourceFilter(this);
			return new CaseSensitiveTransactionSourceFilter(this);
		}

		public Builder sourceName(String name) {
			this.name = name;
			return this;
		}

		public Builder caseInsensitive() {
			insensitive = true;
			return this;
		}
	}

	public static class CaseSensitiveTransactionSourceFilter implements
			TransactionFilter {

		private final String name;

		private CaseSensitiveTransactionSourceFilter(Builder builder) {
			name = builder.name;
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return name.equals(transaction.getSource().getName());
		}

		@Override
		public String toString() {
			return String.format("source =(case) \"%s\"", name);
		}
	}

	public static class CaseInsensitiveTransactionSourceFilter implements
			TransactionFilter {
		private final String name;

		private CaseInsensitiveTransactionSourceFilter(Builder builder) {
			name = builder.name;
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return name.equalsIgnoreCase(transaction.getSource().getName());
		}

		@Override
		public String toString() {
			return String.format("source = \"%s\"", name);
		}
	}

}
