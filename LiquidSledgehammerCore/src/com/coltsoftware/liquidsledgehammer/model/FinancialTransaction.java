package com.coltsoftware.liquidsledgehammer.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import org.joda.time.LocalDate;

import com.coltsoftware.liquidsledgehammer.collections.GroupValueGenerator;
import com.coltsoftware.liquidsledgehammer.collections.GroupValues;

public final class FinancialTransaction {

	private final Money value;
	private final String description;
	private final LocalDate date;
	private final String groupPattern;

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

		private long value = 0;
		private String description = "";
		private LocalDate date;
		private String groupPattern;
		private Currency currency;

		public Builder value(long value) {
			this.value = value;
			return this;
		}

		public FinancialTransaction build() {
			if (date == null)
				throw new FinancialTransactionConstructionException();

			Money moneyValue = new Money(value, currency != null ? currency
					: Currency.getInstance(Locale.getDefault()));

			return new FinancialTransaction(moneyValue, description, date,
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

		public Builder currency(Currency currency) {
			this.currency = currency;
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
		GroupValues values = new GroupValueGenerator().getGroupValues(this);
		for (String group : values)
			arrayList.add(new SubTransaction(this, group, values.get(group)));
		return arrayList;
	}
}
