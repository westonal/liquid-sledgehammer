package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.collections.GroupPatternParser;
import com.coltsoftware.liquidsledgehammer.collections.GroupValues;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public final class GroupPatternParserTests extends MoneyTestBase {

	private Builder builder;
	private GroupPatternParser gvg;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().date(2014, 5, 1);
		gvg = new GroupPatternParser();
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
		GroupValues groupValues = getValues(gbp(99), "one");
		assertTrue(groupValues.getUnassigned().isZero());
		assertEquals(gbp(99), groupValues.get("one"));
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

	@Test
	public void percentage_allowed() {
		GroupValues groupValues = getValues(euro(10000), "one=50%");
		assertEquals(2, count(groupValues));
		assertEquals(Money.fromString("50", euro), groupValues.get("one"));
		assertEquals(Money.fromString("50", euro), groupValues.getUnassigned());
	}

	@Test
	public void two_percentages_allowed() {
		GroupValues groupValues = getValues(euro(10000), "one=50%,two=25%");
		assertEquals(3, count(groupValues));
		assertEquals(Money.fromString("50", euro), groupValues.get("one"));
		assertEquals(Money.fromString("25", euro), groupValues.get("two"));
		assertEquals(Money.fromString("25", euro), groupValues.getUnassigned());
	}
	
	@Test
	public void two_percentages_allowed_different_percentages() {
		GroupValues groupValues = getValues(euro(10000), "one=10%,two=30%");
		assertEquals(3, count(groupValues));
		assertEquals(Money.fromString("10", euro), groupValues.get("one"));
		assertEquals(Money.fromString("30", euro), groupValues.get("two"));
		assertEquals(Money.fromString("60", euro), groupValues.getUnassigned());
	}
	
	@Test
	public void fractional_percentages_allowed() {
		GroupValues groupValues = getValues(euro(20000), "one=10%,two=12.5%");
		assertEquals(3, count(groupValues));
		assertEquals(Money.fromString("20", euro), groupValues.get("one"));
		assertEquals(Money.fromString("25", euro), groupValues.get("two"));
		assertEquals(Money.fromString("155", euro), groupValues.getUnassigned());
	}
	
	@Test
	public void group_values_push_remaining_for_zero() {
		GroupValues groupValues = getValues(usd(0), null);
		assertEquals(0, count(groupValues));
		groupValues.pushRemainingToGroup("TEST");
		assertEquals(0, count(groupValues));
	}

}