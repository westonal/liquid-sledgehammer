package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.collections.GroupValueGenerator;
import com.coltsoftware.liquidsledgehammer.collections.GroupValues;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public final class TransactionGroupTests extends MoneyTestBase {

	private Builder builder;
	private GroupValueGenerator gvg;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().date(2014, 5, 1);
		gvg = new GroupValueGenerator();
	}

	private GroupValues getValues(Money value, String pattern) {
		FinancialTransaction transaction = builder.value(value)
				.groupPattern(pattern).build();
		GroupValues groupValues = gvg.getGroupValues(transaction);
		return groupValues;
	}

	@Test
	public void no_groups_specified() {
		GroupValues groupValues = getValues(usd(99), null);
		assertEquals(usd(99), groupValues.getUnassigned());
	}

	@Test
	public void value_of_unknown_group() {
		GroupValues groupValues = getValues(usd(99), null);
		assertTrue(groupValues.get("one").isZero());
	}

	@Test
	public void one_group_specified() {
		GroupValues groupValues = getValues(pounds(99), "one");
		assertTrue(groupValues.getUnassigned().isZero());
		assertEquals(pounds(99), groupValues.get("one"));
	}

	@Test
	public void value_to_one_group_specified() {
		GroupValues groupValues = getValues(euro(9900), "one=10");
		assertEquals(Money.fromString("89", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("10", euro), groupValues.get("one"));
		assertEquals(Money.fromString("89", euro), groupValues.get(""));
	}

	@Test
	public void value_to_two_groups_specified() {
		GroupValues groupValues = getValues(euro(9900), "one=10,two=30");
		assertEquals(Money.fromString("59", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("10", euro), groupValues.get("one"));
		assertEquals(Money.fromString("30", euro), groupValues.get("two"));
		assertEquals(Money.fromString("59", euro), groupValues.get(""));
	}

	@Test
	public void value_to_two_groups_one_specified() {
		GroupValues groupValues = getValues(euro(9900), "one=10,two");
		assertEquals(Money.fromString("0", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("10", euro), groupValues.get("one"));
		assertEquals(Money.fromString("89", euro), groupValues.get("two"));
		assertEquals(Money.fromString("0", euro), groupValues.get(""));
	}

	@Test
	public void one_group_value_specified_has_one_value() {
		GroupValues groupValues = getValues(euro(99), "one");
		assertEquals(1, count(groupValues));
	}

	@Test
	public void one_group_value_exactly_specified_has_one_value() {
		GroupValues groupValues = getValues(euro(9900), "one=99");
		assertEquals(1, count(groupValues));
	}

	@Test
	public void one_group_value_exactly_specified_has_one_value_specified_with_incorrect_sign() {
		GroupValues groupValues = getValues(euro(9900), "one=-99");
		assertEquals(1, count(groupValues));
		assertEquals(Money.fromString("99", euro), groupValues.get("one"));
	}

	@Test
	public void one_group_value_exactly_specified_negative_has_one_value() {
		GroupValues groupValues = getValues(euro(-9900), "one=-99");
		assertEquals(1, count(groupValues));
	}

	@Test
	public void one_group_value_specified_has_two_values() {
		GroupValues groupValues = getValues(euro(99), "one=10");
		assertEquals(2, count(groupValues));
	}

	@Test
	public void one_group_value_specified_negative_has_two_values() {
		GroupValues groupValues = getValues(euro(-99), "one=-10");
		assertEquals(2, count(groupValues));
	}

	@Test
	public void one_group_but_overshoots_the_available() {
		GroupValues groupValues = getValues(euro(9900), "one=100");
		assertEquals(2, count(groupValues));
		assertEquals(Money.fromString("-1", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("100", euro), groupValues.get("one"));
		assertEquals(Money.fromString("-1", euro), groupValues.get(""));
	}

	@Test
	public void one_group_but_overshoots_the_available_negative() {
		GroupValues groupValues = getValues(euro(-9900), "one=-100");
		assertEquals(2, count(groupValues));
		assertEquals(Money.fromString("1", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("-100", euro), groupValues.get("one"));
		assertEquals(Money.fromString("1", euro), groupValues.get(""));
	}

	@Test
	public void two_groups_mixed_signs() {
		GroupValues groupValues = getValues(euro(9900), "one=100,two=-1");
		assertEquals(1, count(groupValues));
		assertEquals(Money.fromString("99", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("99", euro), groupValues.get(""));
	}

}