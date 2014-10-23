package com.coltsoftware.liquidsledgehammer.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.filters.TransactionDateFilter.Builder;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class TransactionDateFilterTests extends TransactionFilterTestBase {

	private Builder builder;

	@Before
	public void setup() {
		builder = new TransactionDateFilter.Builder();
	}

	private static FinancialTransaction newForDate(int year, int month, int day) {
		return newTransactionBuilder().date(year, month, day).build();
	}

	@Test
	public void without_specifying_parameters_returns_null_filter() {
		TransactionFilter f = builder.build();
		Assert.assertTrue(f instanceof NullTransactionFilter);
	}

	@Test
	public void minimum_date_allows_exact_match() {
		TransactionFilter f = builder.setMinimumDate(2014, 10, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void minimum_date_disallows_day_before() {
		TransactionFilter f = builder.setMinimumDate(2014, 10, 21).build();
		Assert.assertFalse(f.allow(newForDate(2014, 10, 20)));
	}

	@Test
	public void minimum_date_disallows_month_before() {
		TransactionFilter f = builder.setMinimumDate(2014, 11, 21).build();
		Assert.assertFalse(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void minimum_date_disallows_year_before() {
		TransactionFilter f = builder.setMinimumDate(2015, 10, 21).build();
		Assert.assertFalse(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void minimum_date_allows_day_after() {
		TransactionFilter f = builder.setMinimumDate(2014, 10, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 22)));
	}

	@Test
	public void minimum_date_allows_month_after() {
		TransactionFilter f = builder.setMinimumDate(2014, 9, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void minimum_date_allows_year_after() {
		TransactionFilter f = builder.setMinimumDate(2013, 10, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void maximum_date_allows_exact_match() {
		TransactionFilter f = builder.setMaximumDate(2014, 10, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void maximum_date_allows_day_before() {
		TransactionFilter f = builder.setMaximumDate(2014, 10, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 20)));
	}

	@Test
	public void maximum_date_allows_month_before() {
		TransactionFilter f = builder.setMaximumDate(2014, 11, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void maximum_date_allows_year_before() {
		TransactionFilter f = builder.setMaximumDate(2015, 10, 21).build();
		Assert.assertTrue(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void maximum_date_disallows_day_after() {
		TransactionFilter f = builder.setMaximumDate(2014, 10, 21).build();
		Assert.assertFalse(f.allow(newForDate(2014, 10, 22)));
	}

	@Test
	public void maximum_date_disallows_month_after() {
		TransactionFilter f = builder.setMaximumDate(2014, 9, 21).build();
		Assert.assertFalse(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void maximum_date_disallows_year_after() {
		TransactionFilter f = builder.setMaximumDate(2013, 10, 21).build();
		Assert.assertFalse(f.allow(newForDate(2014, 10, 21)));
	}

	@Test
	public void minimum_and_maximum_date_allows_exact_match_on_min() {
		TransactionFilter f = builder.setMinimumDate(2012, 8, 2)
				.setMaximumDate(2013, 9, 10).build();
		Assert.assertTrue(f.allow(newForDate(2012, 8, 2)));
	}
	
	@Test
	public void minimum_and_maximum_date_allows_exact_match_on_max() {
		TransactionFilter f = builder.setMinimumDate(2012, 8, 2)
				.setMaximumDate(2013, 9, 10).build();
		Assert.assertTrue(f.allow(newForDate(2013, 9, 10)));
	}
	
	@Test
	public void minimum_and_maximum_date_disallows_day_before_min() {
		TransactionFilter f = builder.setMinimumDate(2012, 8, 2)
				.setMaximumDate(2013, 9, 10).build();
		Assert.assertFalse(f.allow(newForDate(2012, 8, 1)));
	}
	
	@Test
	public void minimum_and_maximum_date_disallows_day_after_max() {
		TransactionFilter f = builder.setMinimumDate(2012, 8, 2)
				.setMaximumDate(2013, 9, 10).build();
		Assert.assertFalse(f.allow(newForDate(2013, 9, 11)));
	}

}
