package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public class FinancialTransactionTests {

	private Builder builder;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().date(2014, 5, 1);
	}

	@Test
	public void default_value_is_0() {
		FinancialTransaction transaction = builder.build();
		assertEquals(0, transaction.getValue().getValue());
	}

	@Test
	public void can_specifty_transaction_value() {
		FinancialTransaction transaction = builder.value(1).build();
		assertEquals(1, transaction.getValue().getValue());
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
		assertEquals(new LocalDate(2020, 3, 15), transaction.getDate());
	}

	@Test
	public void can_specify_alternative_date_by_y_m_d() {
		FinancialTransaction transaction = builder.date(2045, 1, 31).build();
		assertEquals(new LocalDate(2045, 1, 31), transaction.getDate());
	}

}
