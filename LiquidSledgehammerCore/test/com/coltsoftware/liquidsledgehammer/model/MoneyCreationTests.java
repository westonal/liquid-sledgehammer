package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyCreationTests extends MoneyTestBase {

	@Test
	public void can_create_by_string_and_currency() {
		assertEquals(gbp(12300), Money.fromString("123", gbp));
	}

	@Test
	public void can_create_by_string_with_pence_and_currency() {
		assertEquals(usd(12312), Money.fromString("123.12", usd));
	}

	@Test
	public void can_create_yen_by_string_with_pence_and_currency() {
		assertEquals(yen(12312), Money.fromString("12312", yen));
	}

	@Test
	public void can_create_with_default_currency() {
		assertEquals(Money.fromString("12312", local),
				Money.fromString("12312"));
	}

	@Test
	public void can_create_negative_by_string_with_pence_and_currency() {
		assertEquals(usd(-12312), Money.fromString("-123.12", usd));
	}

	@Test
	public void can_create_huge_value_by_string_with_pence_and_currency() {
		assertEquals(usd(123153461231212l),
				Money.fromString("1231534612312.12", usd));
	}
}
