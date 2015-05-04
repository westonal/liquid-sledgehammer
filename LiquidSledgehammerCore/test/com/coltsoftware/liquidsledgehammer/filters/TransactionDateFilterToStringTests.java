package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.filters.TransactionDateFilter.Builder;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class TransactionDateFilterToStringTests extends
		TransactionFilterTestBase {

	private Builder builder;

	@Before
	public void setup() {
		builder = new TransactionDateFilter.Builder();
	}

	protected void assertToString(String expected, TransactionFilter f) {
		assertEquals(expected, f.toString());
	}

	@Test
	public void without_specifying_parameters_returns_null_filter() {
		TransactionFilter f = builder.build();
		assertSame(BooleanTransactionFilter.TRUE, f);
	}

	@Test
	public void minimum_date() {
		TransactionFilter f = builder.minimumDate(2014, 10, 21).build();
		String expected = "Date >= 2014-10-21";
		assertToString(expected, f);
	}

	@Test
	public void minimum_date_2() {
		TransactionFilter f = builder.minimumDate(1980, 5, 3).build();
		String expected = "Date >= 1980-05-03";
		assertToString(expected, f);
	}

	@Test
	public void maximum_date() {
		TransactionFilter f = builder.maximumDate(2014, 10, 21).build();
		String expected = "Date <= 2014-10-21";
		assertToString(expected, f);
	}

	@Test
	public void maximum_date_2() {
		TransactionFilter f = builder.maximumDate(1980, 5, 3).build();
		String expected = "Date <= 1980-05-03";
		assertToString(expected, f);
	}
}
