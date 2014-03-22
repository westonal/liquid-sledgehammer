package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class MoneyTests extends MoneyTestBase {

	@Test
	public void can_create_and_read_value() {
		Money m = new Money(123, gbp);
		assertEquals(123, m.getValue());
	}

	@Test
	public void can_create_and_read_value_without_specifying_currency() {
		Money m = new Money(50);
		assertEquals(50, m.getValue());
		assertEquals(m.getCurrency(), local);
	}

	@Test
	public void can_create_and_read_negative_value() {
		Money m = new Money(-10, gbp);
		assertEquals(-10, m.getValue());
	}

	@Test
	public void can_create_and_read_currency() {
		Money m = new Money(123, gbp);
		assertEquals(gbp, m.getCurrency());
	}

	@Test
	public void can_create_and_read_us_currency() {
		Money m = new Money(123, usd);
		assertEquals(usd, m.getCurrency());
	}

	@Test
	public void is_zero() {
		assertTrue(new Money(0, usd).isZero());
	}

	@Test
	public void is_zero_UK() {
		assertTrue(new Money(0, gbp).isZero());
	}

	@Test
	public void is_non_zero_positive() {
		assertFalse(new Money(10, gbp).isZero());
	}

	@Test
	public void is_non_zero_negative() {
		assertFalse(new Money(-10, gbp).isZero());
	}

	@Test
	public void is_negative_of_negative_number() {
		assertTrue(new Money(-10, gbp).isNegative());
	}

	@Test
	public void is_negative_of_positive_number() {
		assertFalse(new Money(10, gbp).isNegative());
	}

	@Test
	public void is_negative_of_zero_number() {
		assertFalse(new Money(0, gbp).isNegative());
	}

	@Test
	public void is_positive_of_positive_number() {
		assertTrue(new Money(10, gbp).isPositive());
	}

	@Test
	public void is_positive_of_negative_number() {
		assertFalse(new Money(-10, gbp).isPositive());
	}

	@Test
	public void is_positive_of_zero_number() {
		assertTrue(new Money(0, gbp).isPositive());
	}

	@Test
	public void same_sign_two_positives() {
		assertTrue(new Money(100, gbp).sameSignAs(new Money(100, gbp)));
	}

	@Test
	public void same_sign_two_negatives() {
		assertTrue(new Money(-100, gbp).sameSignAs(new Money(-50, gbp)));
	}

	@Test
	public void not_same_sign_first_negative() {
		assertFalse(new Money(-100, gbp).sameSignAs(new Money(50, gbp)));
	}

	@Test
	public void not_same_sign_first_positive() {
		assertFalse(new Money(123, gbp).sameSignAs(new Money(-412, gbp)));
	}

	@Test
	public void zeros_same_sign_as_positive() {
		assertTrue(new Money(0, gbp).sameSignAs(new Money(412, gbp)));
	}

	@Test
	public void zeros_same_sign_as_negative() {
		assertTrue(new Money(0, gbp).sameSignAs(new Money(-412, gbp)));
	}

	@Test
	public void zeros_same_sign_as_positive_zero_second() {
		assertTrue(new Money(412, gbp).sameSignAs(new Money(0, gbp)));
	}

	@Test
	public void zeros_same_sign_as_negative_zero_second() {
		assertTrue(new Money(-412, gbp).sameSignAs(new Money(0, gbp)));
	}

	@Test
	public void same_sign_empty() {
		ArrayList<Money> moneys = new ArrayList<Money>();
		assertTrue(Money.allSameSign(moneys));
		assertTrue(Money.allPositiveOrZero(moneys));
		assertTrue(Money.allNegativeOrZero(moneys));
	}

	@Test
	public void same_sign_set_two_items() {
		ArrayList<Money> moneys = new ArrayList<Money>();
		moneys.add(gbp(5));
		moneys.add(gbp(50));
		assertTrue(Money.allSameSign(moneys));
		assertTrue(Money.allPositiveOrZero(moneys));
		assertFalse(Money.allNegativeOrZero(moneys));
	}

	@Test
	public void same_sign_set_two_items_negative() {
		ArrayList<Money> moneys = new ArrayList<Money>();
		moneys.add(gbp(-5));
		moneys.add(gbp(-50));
		assertTrue(Money.allSameSign(moneys));
		assertFalse(Money.allPositiveOrZero(moneys));
		assertTrue(Money.allNegativeOrZero(moneys));
	}

	@Test
	public void different_sign_set_two_items() {
		ArrayList<Money> moneys = new ArrayList<Money>();
		moneys.add(gbp(5));
		moneys.add(gbp(-50));
		assertFalse(Money.allSameSign(moneys));
		assertFalse(Money.allPositiveOrZero(moneys));
		assertFalse(Money.allNegativeOrZero(moneys));
	}

	@Test
	public void different_sign_set_two_items_with_zero() {
		ArrayList<Money> moneys = new ArrayList<Money>();
		moneys.add(gbp(5));
		moneys.add(gbp(0));
		moneys.add(gbp(-50));
		assertFalse(Money.allSameSign(moneys));
		assertFalse(Money.allPositiveOrZero(moneys));
		assertFalse(Money.allNegativeOrZero(moneys));
	}

	@Test
	public void same_sign_set_two_items_with_zero() {
		ArrayList<Money> moneys = new ArrayList<Money>();
		moneys.add(gbp(5));
		moneys.add(gbp(0));
		moneys.add(gbp(5));
		assertTrue(Money.allSameSign(moneys));
		assertTrue(Money.allPositiveOrZero(moneys));
		assertFalse(Money.allNegativeOrZero(moneys));
	}

	@Test
	public void same_sign_set_two_items_with_zero_negative() {
		ArrayList<Money> moneys = new ArrayList<Money>();
		moneys.add(gbp(-5));
		moneys.add(gbp(0));
		moneys.add(gbp(-5));
		assertTrue(Money.allSameSign(moneys));
		assertFalse(Money.allPositiveOrZero(moneys));
		assertTrue(Money.allNegativeOrZero(moneys));
	}

}
