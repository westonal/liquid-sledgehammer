package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;

public final class MoneyComparisonTests extends MoneyTestBase {

	@Test
	public void equal_values_greater_than() {
		assertFalse(gbp(10).greaterThan(gbp(10)));
	}

	@Test(expected = MoneyCurrencyException.class)
	public void mismatched_currency_greater_than() {
		gbp(10).greaterThan(usd(10));
	}

	@Test
	public void greater_than_positive_values() {
		assertTrue(gbp(11).greaterThan(gbp(10)));
	}

	@Test
	public void greater_than_positive_values_false() {
		assertFalse(gbp(9).greaterThan(gbp(10)));
	}

	@Test
	public void greater_than_negative_values() {
		assertTrue(gbp(-9).greaterThan(gbp(-10)));
	}

	@Test
	public void equal_values_less_than() {
		assertFalse(gbp(10).lessThan(gbp(10)));
	}

	@Test(expected = MoneyCurrencyException.class)
	public void mismatched_currency_less_than() {
		gbp(10).lessThan(usd(10));
	}

	@Test
	public void less_than_positive_values() {
		assertTrue(gbp(10).lessThan(gbp(11)));
	}

	@Test
	public void less_than_positive_values_false() {
		assertFalse(gbp(10).lessThan(gbp(9)));
	}

	@Test
	public void less_than_negative_values() {
		assertTrue(gbp(-10).lessThan(gbp(-9)));
	}
}
