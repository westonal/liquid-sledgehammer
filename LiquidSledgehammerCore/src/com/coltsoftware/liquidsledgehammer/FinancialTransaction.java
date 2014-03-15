package com.coltsoftware.liquidsledgehammer;

import org.joda.time.LocalDate;

public final class FinancialTransaction {

	private final long value;
	private String description;
	private LocalDate date;
	private String groupPattern;

	private FinancialTransaction(long value, String description,
			LocalDate date, String groupPattern) {
		this.value = value;
		this.description = description;
		this.date = date;
		this.groupPattern = groupPattern;
	}

	public long getValue() {
		return value;
	}

	public static class Builder {

		private long value;
		private String description = "";
		private LocalDate date;
		private String groupPattern;

		public Builder value(long value) {
			this.value = value;
			return this;
		}

		public FinancialTransaction build() {
			if (date == null)
				throw new FinancialTransactionConstructionException();

			return new FinancialTransaction(value, description, date,
					groupPattern);
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder date(int year, int month, int day) {
			date = new LocalDate(year, month, day);
			return this;
		}

		public Builder groupPattern(String groupPattern) {
			this.groupPattern = groupPattern;
			return this;
		}

	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getGroupPattern() {
		return groupPattern;
	}
}
