package com.coltsoftware.liquidsledgehammer;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public final class Money {

	private final Currency currency;
	private final long value;

	public Money(long value, Currency instance) {
		this.value = value;
		this.currency = instance;
	}

	public long getValue() {
		return value;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Money)
			return equals(((Money) obj));
		return false;
	}

	public boolean equals(Money other) {
		if (other == null)
			return false;
		return other.value == value && currency.equals(other.currency);
	}

	@Override
	public int hashCode() {
		int hash = (int) value;
		hash *= 31;
		hash += currency.hashCode();
		return hash;
	}

	@Override
	public String toString() {
		int defaultFractionDigits = currency.getDefaultFractionDigits();
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(defaultFractionDigits);
		format.setMaximumFractionDigits(defaultFractionDigits);
		double divider = Math.pow(10, defaultFractionDigits);
		double displayValue = Math.abs(value) / divider;
		String sign = value < 0 ? "-" : "";
		return String.format("%s%s %s", sign,
				currency.getSymbol(Locale.getDefault()),
				format.format(displayValue));
	}

}
