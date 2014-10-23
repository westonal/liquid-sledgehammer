package com.coltsoftware.liquidsledgehammer.filters;

import static com.coltsoftware.liquidsledgehammer.filters.LogicTransactionFilter.and;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class TransactionValueFilter {

	public static class Builder {
		private Money minimumValue;
		private Money maximumValue;

		public TransactionFilter build() {
			if (maximumValue != null && minimumValue != null)
				return and(createMinValueFilter(), createMaxValueFilter());

			if (maximumValue != null)
				return createMaxValueFilter();

			if (minimumValue != null)
				return createMinValueFilter();

			return BooleanTransactionFilter.TRUE;
		}

		private TransactionFilter createMaxValueFilter() {
			return new TransactionValueFilter.MaxValueFilter(maximumValue);
		}

		private TransactionFilter createMinValueFilter() {
			return new TransactionValueFilter.MinValueFilter(minimumValue);
		}

		public Builder minimumValue(Money Value) {
			this.minimumValue = Value;
			return this;
		}

		public Builder maximumValue(Money Value) {
			this.maximumValue = Value;
			return this;
		}
	}

	private static abstract class SingleValueFilter implements
			TransactionFilter {

		protected final Money value;

		private SingleValueFilter(Money value) {
			this.value = value;
		}

		protected boolean sameCurrency(Money tranValue) {
			return tranValue.getCurrency().equals(value.getCurrency());
		}
	}

	private static class MinValueFilter extends SingleValueFilter {

		private MinValueFilter(Money minimumValue) {
			super(minimumValue);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			Money tranValue = transaction.getValue();
			return sameCurrency(tranValue)
					&& tranValue.getValue() >= value.getValue();
		}
	}

	private static class MaxValueFilter extends SingleValueFilter {

		private MaxValueFilter(Money maximumValue) {
			super(maximumValue);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			Money tranValue = transaction.getValue();
			return sameCurrency(tranValue)
					&& tranValue.getValue() <= value.getValue();
		}
	}

}
