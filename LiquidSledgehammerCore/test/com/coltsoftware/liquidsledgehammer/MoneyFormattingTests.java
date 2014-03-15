package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

public final class MoneyFormattingTests {

	@Test
	public void can_format_money_as_string() {
		assertEquals("� 1.23",
				new Money(123, Currency.getInstance(Locale.UK)).toString());
	}

	@Test
	public void can_format_negative_money_as_string() {
		assertEquals("-� 1.23",
				new Money(-123, Currency.getInstance(Locale.UK)).toString());
	}

	@Test
	public void can_format_money_with_no_penny_as_string() {
		assertEquals("� 1.00",
				new Money(100, Currency.getInstance(Locale.UK)).toString());
	}

	@Test
	public void can_format_us_money_as_string() {
		assertEquals("USD 8.34",
				new Money(834, Currency.getInstance(Locale.US)).toString());
	}

	@Test
	public void can_format_yen_money_as_string_respecting_no_fraction() {
		assertEquals("JPY 234",
				new Money(234, Currency.getInstance(Locale.JAPAN)).toString());
	}

}
