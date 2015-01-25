package com.coltsoftware.liquidsledgehammer.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Pattern;

public final class Money {

	public static final Money Zero = new Money(0);
	private final Currency currency;
	private final long minorValue;

	public Money(long minorValue, Currency currency) {
		this.minorValue = minorValue;
		this.currency = currency;
	}

	public Money(long minorValue) {
		this(minorValue, defaultCurrency());
	}

	public static Money fromString(String value, Currency currency) {
		String noDecimals = removeDecimalFromCurrencyString(value, currency);
		long minorValue = Long.parseLong(noDecimals);
		return new Money(minorValue, currency);
	}

	protected static String removeDecimalFromCurrencyString(String value,
			Currency currency) {
		DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
		DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
		char decimalSeparator = symbols.getDecimalSeparator();
		char groupSeparator = symbols.getGroupingSeparator();
		int idx = value.lastIndexOf(decimalSeparator);
		int decimalPlacesSeen = 0;
		if (idx > -1)
			decimalPlacesSeen = value.length() - idx - 1;
		value += zerosForCurrency(currency, decimalPlacesSeen);
		value = value.replaceFirst(Pattern.quote("" + decimalSeparator), "");
		value = value.replace("" + groupSeparator, "");
		return value;
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
		return minorValue;
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
		return other.minorValue == minorValue
				&& currency.equals(other.currency);
	}

	@Override
	public int hashCode() {
		int hash = (int) minorValue;
		hash *= 31;
		hash += currency.hashCode();
		return hash;
	}

	@Override
	public String toString() {
		return toString(true);
	}

	private static double getDecimalFraction(int defaultFractionDigits) {
		return Math.pow(10, defaultFractionDigits);
	}

	public Money subtract(Money otherValue) {
		if (otherValue.isZero())
			return this;
		if (isZero())
			return otherValue.negate();
		return add(otherValue, -otherValue.minorValue);
	}

	public Money negate() {
		return new Money(-minorValue, currency);
	}

	public Money add(Money otherValue) {
		if (isZero())
			return otherValue;
		if (otherValue.isZero())
			return this;
		return add(otherValue, otherValue.minorValue);
	}

	private Money add(Money otherValue, long valueToAdd) {
		if (!currency.equals(otherValue.currency))
			throw new MoneyCurrencyException();

		return new Money(minorValue + valueToAdd, currency);
	}

	public boolean isZero() {
		return minorValue == 0;
	}

	public boolean isNegative() {
		return minorValue < 0;
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

	public String toStringNoSymbol() {
		return toString(false);
	}

	private String toString(boolean symbol) {
		int defaultFractionDigits = currency.getDefaultFractionDigits();
		NumberFormat format = NumberFormat.getInstance();
		if (!symbol)
			format.setGroupingUsed(false);
		format.setMinimumFractionDigits(defaultFractionDigits);
		format.setMaximumFractionDigits(defaultFractionDigits);
		double divider = getDecimalFraction(defaultFractionDigits);
		double displayValue = Math.abs(minorValue) / divider;
		String sign = minorValue < 0 ? "-" : "";
		String valueString = format.format(displayValue);
		return symbol ? String.format("%s%s %s", sign,
				currency.getSymbol(Locale.getDefault()), valueString) : String
				.format("%s%s", sign, valueString);
	}
}
