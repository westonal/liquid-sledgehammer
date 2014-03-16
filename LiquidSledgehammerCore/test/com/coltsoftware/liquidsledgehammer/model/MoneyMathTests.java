package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyMathTests {

	@Test
	public void can_add_same_currency_us() {
		Money m1 = new Money(123, Currency.getInstance(Locale.US));
		Money m2 = new Money(200, Currency.getInstance(Locale.US));
		Money mtotal = m1.add(m2);
		assertEquals(323, mtotal.getValue());
		assertEquals(Currency.getInstance(Locale.US), mtotal.getCurrency());
	}

	@Test
	public void can_add_same_currency_uk() {
		Money m1 = new Money(123, Currency.getInstance(Locale.UK));
		Money m2 = new Money(200, Currency.getInstance(Locale.UK));
		Money mtotal = m1.add(m2);
		assertEquals(323, mtotal.getValue());
		assertEquals(Currency.getInstance(Locale.UK), mtotal.getCurrency());
	}

	@Test
	public void can_subtract_same_currency_us() {
		Money m1 = new Money(123, Currency.getInstance(Locale.US));
		Money m2 = new Money(23, Currency.getInstance(Locale.US));
		Money mtotal = m1.subtract(m2);
		assertEquals(100, mtotal.getValue());
		assertEquals(Currency.getInstance(Locale.US), mtotal.getCurrency());
	}

	@Test
	public void can_subtract_same_currency_uk() {
		Money m1 = new Money(123, Currency.getInstance(Locale.UK));
		Money m2 = new Money(23, Currency.getInstance(Locale.UK));
		Money mtotal = m1.subtract(m2);
		assertEquals(100, mtotal.getValue());
		assertEquals(Currency.getInstance(Locale.UK), mtotal.getCurrency());
	}

	@Test(expected = MoneyCurrencyException.class)
	public void cant_add_same_currency_us() {
		Money m1 = new Money(123, Currency.getInstance(Locale.US));
		Money m2 = new Money(200, Currency.getInstance(Locale.UK));
		m1.add(m2);
	}
	
	@Test(expected = MoneyCurrencyException.class)
	public void cant_subtract_same_currency_us() {
		Money m1 = new Money(123, Currency.getInstance(Locale.US));
		Money m2 = new Money(200, Currency.getInstance(Locale.UK));
		m1.subtract(m2);
	}

}
