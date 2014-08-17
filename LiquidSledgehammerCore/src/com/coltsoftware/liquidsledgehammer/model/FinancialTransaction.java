package com.coltsoftware.liquidsledgehammer.model;

import java.util.Currency;

import org.joda.time.LocalDate;

public final class FinancialTransaction {

	private final Money value;
	private final String description;
	private final LocalDate date;
	private final String groupPattern;
	private FinancialTransactionSourceInformation source;

	private FinancialTransaction(FinancialTransactionSourceInformation source,
			Money value, String description, LocalDate date, String groupPattern) {
		this.value = value;
		this.description = description;
		this.date = date;
		this.groupPattern = groupPattern;
		this.source = source;
	}

	public Money getValue() {
		return value;
	}

	public static class Builder {

		private Money moneyValue = Money.Zero;
		private String description = "";
		private LocalDate date;
		private String groupPattern;
		private FinancialTransactionSourceInformation source;

		public FinancialTransaction build() {
			if (date == null)
				throw new FinancialTransactionConstructionException();

			if (source == null)
				throw new FinancialTransactionConstructionException();

			return new FinancialTransaction(source, moneyValue, description,
					date, groupPattern);
		}

		public Builder source(FinancialTransactionSourceInformation source) {
			this.source = source;
			return this;
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

		public Builder currency(Currency currency) {
			if (!moneyValue.isZero()
					&& currency.getDefaultFractionDigits() != moneyValue
							.getCurrency().getDefaultFractionDigits())
				throw new FinancialTransactionConstructionException();
			return value(new Money(moneyValue.getValue(), currency));
		}

		public Builder value(String value) {
			return value(value, moneyValue.getCurrency());
		}

		public Builder value(String value, Currency currency) {
			return value(Money.fromString(value, currency));
		}

		public Builder value(Money money) {
			moneyValue = money;
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

	public FinancialTransactionSourceInformation getSource() {
		return source;
	}
}
