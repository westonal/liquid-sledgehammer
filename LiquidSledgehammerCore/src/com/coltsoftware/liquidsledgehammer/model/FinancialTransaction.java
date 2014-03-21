package com.coltsoftware.liquidsledgehammer.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import org.joda.time.LocalDate;


public final class FinancialTransaction {

	private final Money value;
	private String description;
	private LocalDate date;
	private String groupPattern;

	private FinancialTransaction(Money value, String description,
			LocalDate date, String groupPattern) {
		this.value = value;
		this.description = description;
		this.date = date;
		this.groupPattern = groupPattern;
	}

	public Money getValue() {
		return value;
	}

	public static class Builder {

		private Money value = Money.Zero;
		private String description = "";
		private LocalDate date;
		private String groupPattern;

		public Builder value(long value) {
			this.value = new Money(value, Currency.getInstance(Locale
					.getDefault()));
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

	public Iterable<SubTransaction> getSubTransactions() {
		ArrayList<SubTransaction> arrayList = new ArrayList<SubTransaction>();
		arrayList.add(new SubTransaction(this));
		return arrayList;
	}
}
