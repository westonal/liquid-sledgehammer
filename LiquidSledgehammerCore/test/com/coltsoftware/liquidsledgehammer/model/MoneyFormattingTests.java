package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;

public final class MoneyFormattingTests extends MoneyTestBase {

	@Test
	public void can_format_money_as_string() {
		assertEquals("£ 1.23", gbp(123).toString());
	}

	@Test
	public void can_format_negative_money_as_string() {
		assertEquals("-£ 1.23", gbp(-123).toString());
	}

	@Test
	public void can_format_money_with_no_penny_as_string() {
		assertEquals("£ 1.00", gbp(100).toString());
	}

	@Test
	public void can_format_us_money_as_string() {
		assertEquals("USD 8.34", usd(834).toString());
	}

	@Test
	public void can_format_euro_money_as_string() {
		assertEquals("€ 8.34", euro(834).toString());
	}

	@Test
	public void can_format_negative_euro_money_as_string() {
		assertEquals("-€ 0.34", euro(-34).toString());
	}

	@Test
	public void can_format_yen_money_as_string_respecting_no_fraction() {
		assertEquals("JPY 234", yen(234).toString());
	}

	@Test
	public void can_format_gbp_money_without_symbol() {
		assertEquals("1.34", gbp(134).toStringNoSymbol());
	}

	@Test
	public void can_format_negative_euro_money_without_symbol() {
		assertEquals("-0.34", euro(-34).toStringNoSymbol());
	}

	@Test
	public void can_format_large_value_gbp_money_with_thousand_seperators() {
		assertEquals("£ 1,340.00", gbp(134000).toString());
	}

	@Test
	public void can_format_large_value_gbp_money_without_thousand_seperators() {
		assertEquals("1340.00", gbp(134000).toStringNoSymbol());
	}

}
