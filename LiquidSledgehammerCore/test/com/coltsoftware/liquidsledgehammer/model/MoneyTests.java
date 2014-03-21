package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyTests {

	@Test
	public void can_create_and_read_value() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		assertEquals(123, m.getValue());
	}

	@Test
	public void can_create_and_read_value_without_specifying_currency() {
		Money m = new Money(50);
		assertEquals(50, m.getValue());
		assertEquals(m.getCurrency(), Currency.getInstance(Locale.getDefault()));
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
	
	@Test
	public void is_zero() {
		assertTrue(new Money(0, Currency.getInstance(Locale.US)).isZero());
	}
	
	@Test
	public void is_zero_UK() {
		assertTrue(new Money(0, Currency.getInstance(Locale.UK)).isZero());
	}
	
	@Test
	public void is_non_zero_positive() {
		assertFalse(new Money(10, Currency.getInstance(Locale.UK)).isZero());
	}
	
	@Test
	public void is_non_zero_negative() {
		assertFalse(new Money(-10, Currency.getInstance(Locale.UK)).isZero());
	}

}
