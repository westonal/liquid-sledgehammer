package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.FinancialTransaction.Builder;

public class FinancialTransactionTests {

	private Builder builder;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().date(2014, 5, 1);
	}

	@Test
	public void default_value_is_0() {
		FinancialTransaction transaction = builder.build();
		assertEquals(0, transaction.getValue());
	}

	@Test
	public void can_specifty_transaction_value() {
		FinancialTransaction transaction = builder.value(1).build();
		assertEquals(1, transaction.getValue());
	}

	@Test
	public void default_description_is_empty_string() {
		FinancialTransaction transaction = builder.build();
		assertEquals("", transaction.getDescription());
	}

	@Test
	public void can_specify_description() {
		FinancialTransaction transaction = builder.description("Desc").build();
		assertEquals("Desc", transaction.getDescription());
	}

	@Test(expected = FinancialTransactionConstructionException.class)
	public void must_have_date() {
		new FinancialTransaction.Builder().build();
	}

	@Test
	public void can_specify_date_by_y_m_d() {
		FinancialTransaction transaction = builder.date(2020, 3, 15).build();
		assertEquals(new GregorianCalendar(2020, Calendar.MARCH, 15).getTime(),
				transaction.getDate());
	}

	@Test
	public void can_specify_alternative_date_by_y_m_d() {
		FinancialTransaction transaction = builder.date(2045, 1, 31).build();
		assertEquals(
				new GregorianCalendar(2045, Calendar.JANUARY, 31).getTime(),
				transaction.getDate());
	}

}
