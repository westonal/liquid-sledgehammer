package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

public final class MoneyEqualityTests extends BaseTest {

	@Test
	public void are_equal() {
		Money m1 = new Money(123, Currency.getInstance(Locale.UK));
		Money m2 = new Money(123, Currency.getInstance(Locale.UK));
		assertEquals(m1, m2);
		assertEquals(m2, m1);
	}

	@Test
	public void are_not_equal_by_value() {
		Money m1 = new Money(123, Currency.getInstance(Locale.UK));
		Money m2 = new Money(124, Currency.getInstance(Locale.UK));
		assertNotEqual(m1, m2);
	}

	@Test
	public void are_not_equal_by_class() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		assertNotEqual(m, new Object());
	}

	@Test
	public void are_not_equal_by_class_reversed() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		assertNotEqual(new Object(), m);
	}

	@Test
	public void are_not_equal_to_null() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		assertNotEqual(m, null);
	}

	@Test
	public void are_not_equal_to_null_reversed() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		assertNotEqual(null, m);
	}

	@Test
	public void are_not_equal_to_null_when_using_money_overload() {
		Money m = new Money(123, Currency.getInstance(Locale.UK));
		Money other = null;
		assertFalse(m.equals(other));
	}
	
	@Test
	public void are_not_equal_due_to_currency() {
		Money m1 = new Money(123, Currency.getInstance(Locale.UK));
		Money m2 = new Money(123, Currency.getInstance(Locale.US));
		assertNotEqual(m1, m2);
		assertNotEqual(m2, m1);
	}
	
	@Test
	public void hash_are_equal() {
		Money m1 = new Money(2346, Currency.getInstance(Locale.UK));
		Money m2 = new Money(2346, Currency.getInstance(Locale.UK));
		assertEquals(m1.hashCode(), m2.hashCode());
	}
	
	@Test
	public void hash_are_not_equal() {
		Money m1 = new Money(2346, Currency.getInstance(Locale.UK));
		Money m2 = new Money(1234, Currency.getInstance(Locale.UK));
		assertNotEqual(m1.hashCode(), m2.hashCode());
	}
	
	@Test
	public void hash_are_not_equal_due_to_currency() {
		Money m1 = new Money(34734, Currency.getInstance(Locale.UK));
		Money m2 = new Money(34734, Currency.getInstance(Locale.US));
		assertNotEqual(m1.hashCode(), m2.hashCode());
	}

}
