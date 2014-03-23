package com.coltsoftware.liquidsledgehammer.model;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public final class Money {

	public static final Money Zero = new Money(0);
	private final Currency currency;
	private final long value;

	public Money(long value, Currency currency) {
		this.value = value;
		this.currency = currency;
	}

	public Money(long value) {
		this(value, defaultCurrency());
	}

	public static Money fromString(String value, Currency currency) {
		int idx = value.lastIndexOf('.');
		int decimalPlacesSeen;
		if (idx == -1)
			decimalPlacesSeen = 0;
		else
			decimalPlacesSeen = value.length() - idx - 1;
		value += zerosForCurrency(currency, decimalPlacesSeen);
		value = value.replaceFirst("\\.", "");
		long value2 = (long) (Double.parseDouble(value));
		return new Money(value2, currency);
	}

	private static String zerosForCurrency(Currency currency,
			int decimalPlacesSeen) {
		int n = currency.getDefaultFractionDigits();
		int decimalPlacesToAdd = n - decimalPlacesSeen;
		if (decimalPlacesToAdd < 0)
			throw new MoneyFromStringException();
		return nZeros(decimalPlacesToAdd);
	}

	private static String nZeros(int n) {
		if (n == 0)
			return "";
		return String.format(String.format("%%0%dd", n), 0);
	}

	public static Money fromString(String value) {
		return fromString(value, defaultCurrency());
	}

	private static Currency defaultCurrency() {
		return Currency.getInstance(Locale.getDefault());
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
		double divider = getDecimalFraction(defaultFractionDigits);
		double displayValue = Math.abs(value) / divider;
		String sign = value < 0 ? "-" : "";
		return String.format("%s%s %s", sign,
				currency.getSymbol(Locale.getDefault()),
				format.format(displayValue));
	}

	private static double getDecimalFraction(Currency currency) {
		return getDecimalFraction(currency.getDefaultFractionDigits());
	}

	private static double getDecimalFraction(int defaultFractionDigits) {
		return Math.pow(10, defaultFractionDigits);
	}

	public Money subtract(Money otherValue) {
		if (otherValue.isZero())
			return this;
		if (isZero())
			return otherValue.negate();
		return add(otherValue, -otherValue.value);
	}

	public Money negate() {
		return new Money(-value, currency);
	}

	public Money add(Money otherValue) {
		if (isZero())
			return otherValue;
		if (otherValue.isZero())
			return this;
		return add(otherValue, otherValue.value);
	}

	private Money add(Money otherValue, long valueToAdd) {
		if (!currency.equals(otherValue.currency))
			throw new MoneyCurrencyException();

		return new Money(value + valueToAdd, currency);
	}

	public boolean isZero() {
		return value == 0;
	}

	public boolean isNegative() {
		return value < 0;
	}

	public boolean isPositive() {
		return !isNegative();
	}

	public boolean sameSignAs(Money other) {
		if (isZero() || other.isZero())
			return true;
		if (isNegative() != other.isNegative())
			return false;
		return true;
	}

	public static boolean allSameSign(Iterable<Money> monies) {
		return allPositiveOrZero(monies) || allNegativeOrZero(monies);
	}

	public static boolean allPositiveOrZero(Iterable<Money> monies) {
		for (Money money : monies)
			if (money.isNegative())
				return false;
		return true;
	}

	public static boolean allNegativeOrZero(Iterable<Money> monies) {
		for (Money money : monies)
			if (!money.isZero() && money.isPositive())
				return false;
		return true;
	}

}
