package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyCreationTests {

	@Test
	public void can_create_by_string_and_currency() {
		Money m1 = new Money(12300, Currency.getInstance(Locale.UK));
		Money m2 = Money.fromString("123", Currency.getInstance(Locale.UK));
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_by_string_with_pence_and_currency() {
		Money m1 = new Money(12312, Currency.getInstance(Locale.US));
		Money m2 = Money.fromString("123.12", Currency.getInstance(Locale.US));
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_yen_by_string_with_pence_and_currency() {
		Money m1 = new Money(12312, Currency.getInstance(Locale.JAPAN));
		Money m2 = Money
				.fromString("12312", Currency.getInstance(Locale.JAPAN));
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_sith_default_currency() {
		Money m1 = Money.fromString("12312",
				Currency.getInstance(Locale.getDefault()));
		Money m2 = Money.fromString("12312");
		assertEquals(m1, m2);
	}

	@Test
	public void can_create_negative_by_string_with_pence_and_currency() {
		Money m1 = new Money(-12312, Currency.getInstance(Locale.US));
		Money m2 = Money.fromString("-123.12", Currency.getInstance(Locale.US));
		assertEquals(m1, m2);
	}
}
