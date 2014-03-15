package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

public final class MoneyTests {

	@Test
	public void can_create_and_read_value() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		assertEquals(123, m.getValue());
	}

	@Test
	public void can_create_and_read_negative_value() {
		Money m = new Money(-10, Currency.getInstance(Locale.UK));
		assertEquals(-10, m.getValue());
	}

	@Test
	public void can_create_and_read_currency() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		assertEquals(Currency.getInstance(Locale.UK), m.getCurrency());
	}

	@Test
	public void can_create_and_read_us_currency() {
		Money m = new Money(123, Currency.getInstance(Locale.US));
		assertEquals(Currency.getInstance(Locale.US), m.getCurrency());
	}

}
