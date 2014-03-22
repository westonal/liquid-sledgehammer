package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyCreationTests extends MoneyTestBase {

	@Test
	public void can_create_by_string_and_currency() {
		Money m1 = new Money(12300, gbp);
		Money m2 = Money.fromString("123", gbp);
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_by_string_with_pence_and_currency() {
		Money m1 = new Money(12312, usd);
		Money m2 = Money.fromString("123.12", usd);
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_yen_by_string_with_pence_and_currency() {
		Money m1 = new Money(12312, yen);
		Money m2 = Money.fromString("12312", yen);
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_sith_default_currency() {
		Money m1 = Money.fromString("12312", local);
		Money m2 = Money.fromString("12312");
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_negative_by_string_with_pence_and_currency() {
		Money m1 = new Money(-12312, usd);
		Money m2 = Money.fromString("-123.12", usd);
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_huge_value_by_string_with_pence_and_currency() {
		Money m1 = new Money(123153461231212l, usd);
		Money m2 = Money.fromString("1231534612312.12", usd);
		assertEquals(m1, m2);
	}
}
