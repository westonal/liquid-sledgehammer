package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyMathTests extends MoneyTestBase {

	@Test
	public void can_add_same_currency_us() {
		Money m1 = new Money(123, usd);
		Money m2 = new Money(200, usd);
		Money mtotal = m1.add(m2);
		assertEquals(323, mtotal.getValue());
		assertEquals(usd, mtotal.getCurrency());
	}

	@Test
	public void can_add_same_currency_uk() {
		Money m1 = new Money(123, gbp);
		Money m2 = new Money(200, gbp);
		Money mtotal = m1.add(m2);
		assertEquals(323, mtotal.getValue());
		assertEquals(gbp, mtotal.getCurrency());
	}

	@Test
	public void can_subtract_same_currency_us() {
		Money m1 = new Money(123, usd);
		Money m2 = new Money(23, usd);
		Money mtotal = m1.subtract(m2);
		assertEquals(100, mtotal.getValue());
		assertEquals(usd, mtotal.getCurrency());
	}

	@Test
	public void can_subtract_same_currency_uk() {
		Money m1 = new Money(123, gbp);
		Money m2 = new Money(23, gbp);
		Money mtotal = m1.subtract(m2);
		assertEquals(100, mtotal.getValue());
		assertEquals(gbp, mtotal.getCurrency());
	}

	@Test(expected = MoneyCurrencyException.class)
	public void cant_add_same_currency_us() {
		Money m1 = new Money(123, usd);
		Money m2 = new Money(200, gbp);
		m1.add(m2);
	}

	@Test(expected = MoneyCurrencyException.class)
	public void cant_subtract_same_currency_us() {
		Money m1 = new Money(123, usd);
		Money m2 = new Money(200, gbp);
		m1.subtract(m2);
	}

	@Test
	public void can_add_to_zero_default_currency() {
		Money m1 = new Money(123, local);
		assertEquals(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_usd() {
		Money m1 = new Money(123, usd);
		assertEquals(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_yen() {
		Money m1 = new Money(123, yen);
		assertEquals(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_efficiency_test_usd() {
		Money m1 = new Money(123, usd);
		assertSame(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_efficiency_test_yen() {
		Money m1 = new Money(123, yen);
		assertSame(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_custom_zero_efficiency_test_yen() {
		Money m1 = new Money(123, yen);
		Money zero = new Money(0, yen);
		assertSame(m1, zero.add(m1));
	}

	@Test
	public void can_add_custom_zero_efficiency_test_yen() {
		Money m1 = new Money(123, yen);
		Money zero = new Money(0, yen);
		assertSame(m1, m1.add(zero));
	}

	@Test
	public void can_subtract_from_zero_default_currency() {
		Money m1 = new Money(123, local);
		Money m1Negative = new Money(-123, local);
		assertEquals(m1Negative, Money.Zero.subtract(m1));
	}

	@Test
	public void can_subtract_from_zero_usd() {
		Money m1 = new Money(123, usd);
		Money expected = new Money(-123, usd);
		assertEquals(expected, Money.Zero.subtract(m1));
	}

	@Test
	public void can_subtract_from_zero_yen() {
		Money m1 = new Money(123, yen);
		Money expected = new Money(-123, yen);
		assertEquals(expected, Money.Zero.subtract(m1));
	}

	@Test
	public void can_subtract_zero_efficiency_test() {
		Money m1 = new Money(123, local);
		assertSame(m1, m1.subtract(Money.Zero));
	}

	@Test
	public void can_subtract_zero_from_zero_usd_efficiency_test() {
		Money m1 = new Money(123, usd);
		assertSame(m1, m1.subtract(Money.Zero));
	}

	@Test
	public void can_subtract_custom_zero_from_zero_usd_efficiency_test() {
		Money m1 = new Money(123, usd);
		Money zero = new Money(0, usd);
		assertSame(m1, m1.subtract(zero));
	}

	@Test
	public void can_negative_positive() {
		Money m1 = new Money(123, local);
		Money expected = new Money(-123, local);
		assertEquals(expected, m1.negate());
	}

	@Test
	public void can_negative_negative() {
		Money m1 = new Money(-123, gbp);
		Money expected = new Money(123, gbp);
		assertEquals(expected, m1.negate());
	}

}
