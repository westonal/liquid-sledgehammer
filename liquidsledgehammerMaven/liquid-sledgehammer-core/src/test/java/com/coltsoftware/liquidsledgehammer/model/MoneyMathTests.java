package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyMathTests extends MoneyTestBase {

	@Test
	public void can_add_same_currency_us() {
		assertEquals(usd(323), usd(123).add(usd(200)));
	}

	@Test
	public void can_add_same_currency_uk() {
		assertEquals(gbp(323), gbp(123).add(gbp(200)));
	}

	@Test
	public void can_subtract_same_currency_us() {
		assertEquals(usd(100), usd(123).subtract(usd(23)));
	}

	@Test
	public void can_subtract_same_currency_uk() {
		assertEquals(gbp(100), gbp(123).subtract(gbp(23)));
	}

	@Test(expected = MoneyCurrencyException.class)
	public void cant_add_same_currency_us() {
		usd(123).add(gbp(200));
	}

	@Test(expected = MoneyCurrencyException.class)
	public void cant_subtract_same_currency_us() {
		usd(123).subtract(gbp(23));
	}

	@Test
	public void can_add_to_zero_default_currency() {
		Money m1 = local(123);
		assertEquals(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_usd() {
		Money m1 = usd(123);
		assertEquals(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_yen() {
		Money m1 = yen(123);
		assertEquals(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_efficiency_test_usd() {
		Money m1 = usd(123);
		assertSame(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_zero_efficiency_test_yen() {
		Money m1 = yen(123);
		assertSame(m1, Money.Zero.add(m1));
	}

	@Test
	public void can_add_to_custom_zero_efficiency_test_yen() {
		Money m1 = yen(123);
		Money zero = yen(0);
		assertSame(m1, zero.add(m1));
	}

	@Test
	public void can_add_custom_zero_efficiency_test_yen() {
		Money m1 = yen(123);
		Money zero = yen(0);
		assertSame(m1, m1.add(zero));
	}

	@Test
	public void can_subtract_from_zero_default_currency() {
		Money m1 = local(123);
		Money expected = local(-123);
		assertEquals(expected, Money.Zero.subtract(m1));
	}

	@Test
	public void can_subtract_from_zero_usd() {
		Money m1 = usd(123);
		Money expected = usd(-123);
		assertEquals(expected, Money.Zero.subtract(m1));
	}

	@Test
	public void can_subtract_from_zero_yen() {
		Money m1 = yen(123);
		Money expected = yen(-123);
		assertEquals(expected, Money.Zero.subtract(m1));
	}

	@Test
	public void can_subtract_zero_efficiency_test() {
		Money m1 = local(123);
		assertSame(m1, m1.subtract(Money.Zero));
	}

	@Test
	public void can_subtract_zero_from_zero_usd_efficiency_test() {
		Money m1 = usd(123);
		assertSame(m1, m1.subtract(Money.Zero));
	}

	@Test
	public void can_subtract_custom_zero_from_zero_usd_efficiency_test() {
		Money m1 = usd(123);
		Money zero = usd(0);
		assertSame(m1, m1.subtract(zero));
	}

	@Test
	public void can_negative_positive() {
		Money m1 = local(123);
		Money expected = local(-123);
		assertEquals(expected, m1.negate());
	}

	@Test
	public void can_negative_negative() {
		Money m1 = gbp(123);
		Money expected = gbp(-123);
		assertEquals(expected, m1.negate());
	}

}
