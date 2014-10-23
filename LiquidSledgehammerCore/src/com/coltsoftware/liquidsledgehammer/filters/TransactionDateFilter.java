package com.coltsoftware.liquidsledgehammer.filters;

import static com.coltsoftware.liquidsledgehammer.filters.LogicTransactionFilter.and;

import org.joda.time.LocalDate;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class TransactionDateFilter {

	public static class Builder {
		private LocalDate minimumDate;
		private LocalDate maximumDate;

		public TransactionFilter build() {
			if (maximumDate != null && minimumDate != null)
				return and(createMinDateFilter(), createMaxDateFilter());

			if (maximumDate != null)
				return createMaxDateFilter();

			if (minimumDate != null)
				return createMinDateFilter();

			return BooleanTransactionFilter.TRUE;
		}

		private TransactionFilter createMaxDateFilter() {
			return new TransactionDateFilter.MaxDateFilter(maximumDate);
		}

		private TransactionFilter createMinDateFilter() {
			return new TransactionDateFilter.MinDateFilter(minimumDate);
		}

		public Builder minimumDate(int year, int month, int day) {
			return minimumDate(ymdToLocalDate(year, month, day));
		}

		public Builder minimumDate(LocalDate date) {
			this.minimumDate = date;
			return this;
		}

		public Builder maximumDate(int year, int month, int day) {
			return maximumDate(ymdToLocalDate(year, month, day));
		}

		public Builder maximumDate(LocalDate date) {
			this.maximumDate = date;
			return this;
		}

		private LocalDate ymdToLocalDate(int year, int month, int day) {
			return new LocalDate(year, month, day);
		}
	}

	private static abstract class SingleDateFilter implements TransactionFilter {

		protected final LocalDate date;

		private SingleDateFilter(LocalDate date) {
			this.date = date;
		}
	}

	private static class MinDateFilter extends SingleDateFilter {

		private MinDateFilter(LocalDate minimumDate) {
			super(minimumDate);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return transaction.getDate().compareTo(date) >= 0;
		}
	}

	private static class MaxDateFilter extends SingleDateFilter {

		private MaxDateFilter(LocalDate maximumDate) {
			super(maximumDate);
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			return transaction.getDate().compareTo(date) <= 0;
		}
	}

}
