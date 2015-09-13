package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyEqualityTests extends MoneyTestBase {

	@Test
	public void are_equal() {
		Money m1 = new Money(123, gbp);
		Money m2 = new Money(123, gbp);
		assertEquals(m1, m2);
		assertEquals(m2, m1);
	}

	@Test
	public void are_not_equal_by_value() {
		Money m1 = new Money(123, gbp);
		Money m2 = new Money(124, gbp);
		assertNotEqual(m1, m2);
	}

	@Test
	public void are_not_equal_by_class() {
		Money m = new Money(123, gbp);
		assertNotEqual(m, new Object());
	}

	@Test
	public void are_not_equal_by_class_reversed() {
		Money m = new Money(123, gbp);
		assertNotEqual(new Object(), m);
	}

	@Test
	public void are_not_equal_to_null() {
		Money m = new Money(123, gbp);
		assertNotEqual(m, null);
	}

	@Test
	public void are_not_equal_to_null_reversed() {
		Money m = new Money(123, gbp);
		assertNotEqual(null, m);
	}

	@Test
	public void are_not_equal_to_null_when_using_money_overload() {
		Money m = new Money(123, gbp);
		Money other = null;
		assertFalse(m.equals(other));
	}

	@Test
	public void are_not_equal_due_to_currency() {
		Money m1 = new Money(123, gbp);
		Money m2 = new Money(123, usd);
		assertNotEqual(m1, m2);
		assertNotEqual(m2, m1);
	}

	@Test
	public void hash_are_equal() {
		Money m1 = new Money(2346, gbp);
		Money m2 = new Money(2346, gbp);
		assertEquals(m1.hashCode(), m2.hashCode());
	}

	@Test
	public void hash_are_not_equal() {
		Money m1 = new Money(2346, gbp);
		Money m2 = new Money(1234, gbp);
		assertNotEqual(m1.hashCode(), m2.hashCode());
	}

	@Test
	public void hash_are_not_equal_due_to_currency() {
		Money m1 = new Money(34734, gbp);
		Money m2 = new Money(34734, usd);
		assertNotEqual(m1.hashCode(), m2.hashCode());
	}

	@Test
	public void zeros_of_different_currencies_are_not_equal() {
		Money m1 = new Money(0, gbp);
		Money m2 = new Money(0, usd);
		assertNotEqual(m1, m2);
	}

}
