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
				return buildRangeFilter();

			if (maximumDate != null)
				return buildMaxDateFilter();

			if (minimumDate != null)
				return buildMinDateFilter();

			return BooleanTransactionFilter.TRUE;
		}

		protected TransactionFilter buildRangeFilter() {
			checkDateRangeValid();

			if (minimumDate.equals(maximumDate))
				return buildExactDateFilter();

			return and(buildMinDateFilter(), buildMaxDateFilter());
		}

		protected void checkDateRangeValid() {
			if (minimumDate.compareTo(maximumDate) > 0)
				throw new DateRangeException();
		}

		private ExactDateFilter buildExactDateFilter() {
			return new ExactDateFilter(minimumDate);
		}

		private TransactionFilter buildMaxDateFilter() {
			return new TransactionDateFilter.MaxDateFilter(maximumDate);
		}

		private TransactionFilter buildMinDateFilter() {
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

		public Builder exactDate(int year, int month, int day) {
			return exactDate(ymdToLocalDate(year, month, day));
		}

		public Builder exactDate(LocalDate date) {
			return minimumDate(date).maximumDate(date);
		}

		private LocalDate ymdToLocalDate(int year, int month, int day) {
			return new LocalDate(year, month, day);
		}

		public Builder exactMonth(int year, int month) {
			LocalDate start = ymdToLocalDate(year, month, 1);
			return minimumDate(start).maximumDate(start.plusMonths(1).minusDays(1));
		}
	}

	private static abstract class SingleDateFilter implements TransactionFilter {

		private final LocalDate date;

		private SingleDateFilter(LocalDate date) {
			this.date = date;
		}

		@Override
		public boolean allow(FinancialTransaction transaction) {
			int compared = transaction.getDate().compareTo(date);
			return evaluateCompareValue(compared);
		}

		protected abstract boolean evaluateCompareValue(int compared);
	}

	private static class MinDateFilter extends SingleDateFilter {

		private MinDateFilter(LocalDate minimumDate) {
			super(minimumDate);
		}

		@Override
		protected boolean evaluateCompareValue(int compared) {
			return compared >= 0;
		}
	}

	private static class MaxDateFilter extends SingleDateFilter {

		private MaxDateFilter(LocalDate maximumDate) {
			super(maximumDate);
		}

		@Override
		protected boolean evaluateCompareValue(int compared) {
			return compared <= 0;
		}
	}

	private static class ExactDateFilter extends SingleDateFilter {

		private ExactDateFilter(LocalDate date) {
			super(date);
		}

		@Override
		protected boolean evaluateCompareValue(int compared) {
			return compared == 0;
		}
	}

}
