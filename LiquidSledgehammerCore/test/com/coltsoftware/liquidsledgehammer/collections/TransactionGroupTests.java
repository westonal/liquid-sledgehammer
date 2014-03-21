package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.BaseTest;
import com.coltsoftware.liquidsledgehammer.collections.GroupValueGenerator;
import com.coltsoftware.liquidsledgehammer.collections.GroupValues;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public final class TransactionGroupTests extends BaseTest {

	private Builder builder;
	private GroupValueGenerator gvg;
	private final Currency euro = Currency.getInstance(Locale.GERMANY);

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().date(2014, 5, 1);
		gvg = new GroupValueGenerator();
	}

	private GroupValues getValues(long value, String pattern) {
		FinancialTransaction transaction = builder.value(value).currency(euro)
				.groupPattern(pattern).build();
		GroupValues groupValues = gvg.getGroupValues(transaction);
		return groupValues;
	}

	@Test
	public void no_groups_specified() {
		GroupValues groupValues = getValues(99, null);
		assertEquals(99, groupValues.getUnassigned().getValue());
	}

	@Test
	public void value_of_unknown_group() {
		GroupValues groupValues = getValues(99, null);
		assertEquals(0, groupValues.get("one").getValue());
	}

	@Test
	public void one_group_specified() {
		GroupValues groupValues = getValues(99, "one");
		assertEquals(0, groupValues.getUnassigned().getValue());
		assertEquals(99, groupValues.get("one").getValue());
	}

	@Test
	public void value_to_one_group_specified() {
		GroupValues groupValues = getValues(9900, "one=10");
		assertEquals(Money.fromString("89", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("10", euro), groupValues.get("one"));
		assertEquals(Money.fromString("89", euro), groupValues.get(""));
	}

	@Test
	public void value_to_two_groups_specified() {
		GroupValues groupValues = getValues(9900, "one=10,two=30");
		assertEquals(Money.fromString("59", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("10", euro), groupValues.get("one"));
		assertEquals(Money.fromString("30", euro), groupValues.get("two"));
		assertEquals(Money.fromString("59", euro), groupValues.get(""));
	}

	@Test
	public void value_to_two_groups_one_specified() {
		GroupValues groupValues = getValues(9900, "one=10,two");
		assertEquals(Money.fromString("0", euro), groupValues.getUnassigned());
		assertEquals(Money.fromString("10", euro), groupValues.get("one"));
		assertEquals(Money.fromString("89", euro), groupValues.get("two"));
		assertEquals(Money.fromString("0", euro), groupValues.get(""));
	}

	@Test
	public void one_group_value_specified_has_one_value() {
		GroupValues groupValues = getValues(99, "one");
		assertEquals(1, count(groupValues));
	}

	@Test
	public void one_group_value_exactly_specified_has_one_value() {
		GroupValues groupValues = getValues(9900, "one=99");
		assertEquals(1, count(groupValues));
	}

	@Test
	public void one_group_value_exactly_specified_negative_has_one_value() {
		GroupValues groupValues = getValues(-9900, "one=-99");
		assertEquals(1, count(groupValues));
	}

	@Test
	public void one_group_value_specified_has_two_values() {
		GroupValues groupValues = getValues(99, "one=10");
		assertEquals(2, count(groupValues));
	}

	@Test
	public void one_group_value_specified_negative_has_two_values() {
		GroupValues groupValues = getValues(-99, "one=-10");
		assertEquals(2, count(groupValues));
	}

}