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

	@Test
	public void can_create_987() {
		assertEquals(gbp(987), Money.fromString("9.87", gbp));
	}

	@Test
	public void can_create_980_without_specifying_all_places() {
		assertEquals(gbp(980), Money.fromString("9.8", gbp));
	}

	@Test(expected = MoneyFromStringException.class)
	public void cant_create_gbp_specifying_two_many_places() {
		Money.fromString("9.812", gbp);
	}

	@Test(expected = MoneyFromStringException.class)
	public void cant_create_yen_specifying_two_many_places() {
		Money.fromString("1231.2", yen);
	}
}
