package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.collections.GroupValueGenerator;
import com.coltsoftware.liquidsledgehammer.collections.GroupValues;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public class TransactionGroupTests {

	private Builder builder;
	private GroupValueGenerator gvg;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().date(2014, 5, 1);
		gvg = new GroupValueGenerator();
	}

	private GroupValues getValues(long value, String pattern) {
		FinancialTransaction transaction = builder.value(value)
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
		GroupValues groupValues = getValues(99, "one=10");
		assertEquals(89, groupValues.getUnassigned().getValue());
		assertEquals(10, groupValues.get("one").getValue());
	}
	
	@Test
	public void value_to_two_groups_specified() {
		GroupValues groupValues = getValues(99, "one=10,two=30");
		assertEquals(59, groupValues.getUnassigned().getValue());
		assertEquals(10, groupValues.get("one").getValue());
		assertEquals(30, groupValues.get("two").getValue());
	}
	
	@Test
	public void value_to_two_groups_one_specified() {
		GroupValues groupValues = getValues(99, "one=10,two");
		assertEquals(0, groupValues.getUnassigned().getValue());
		assertEquals(10, groupValues.get("one").getValue());
		assertEquals(89, groupValues.get("two").getValue());
	}

}