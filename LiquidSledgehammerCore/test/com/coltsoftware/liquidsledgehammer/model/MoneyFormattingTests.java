package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyFormattingTests extends MoneyTestBase {

	@Test
	public void can_format_money_as_string() {
		assertEquals("£ 1.23", new Money(123, gbp).toString());
	}

	@Test
	public void can_format_negative_money_as_string() {
		assertEquals("-£ 1.23", new Money(-123, gbp).toString());
	}

	@Test
	public void can_format_money_with_no_penny_as_string() {
		assertEquals("£ 1.00", new Money(100, gbp).toString());
	}

	@Test
	public void can_format_us_money_as_string() {
		assertEquals("USD 8.34", new Money(834, usd).toString());
	}

	@Test
	public void can_format_yen_money_as_string_respecting_no_fraction() {
		assertEquals("JPY 234", new Money(234, yen).toString());
	}

}
