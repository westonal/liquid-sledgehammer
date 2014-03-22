package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public final class FinancialTransactionTests extends MoneyTestBase {

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

	@Test
	public void default_currency_if_not_specified() {
		FinancialTransaction transaction = builder.build();
		assertEquals(local, transaction.getValue().getCurrency());
	}

	@Test
	public void can_specify_currency() {
		FinancialTransaction transaction = builder.currency(gbp).value(123)
				.currency(usd).build();
		assertEquals(usd(123), transaction.getValue());
	}

	@Test
	public void can_specify_currency_first() {
		FinancialTransaction transaction = builder.currency(yen).value(123)
				.build();
		assertEquals(yen(123), transaction.getValue());
	}

	@Test
	public void can_set_whole_value() {
		FinancialTransaction transaction = builder.value(usd(99)).build();
		assertEquals(usd(99), transaction.getValue());
	}

	@Test
	public void can_set_whole_value_by_string() {
		FinancialTransaction transaction = builder.currency(yen).value("99")
				.build();
		assertEquals(yen(99), transaction.getValue());
	}

	@Test
	public void can_set_whole_value_by_string_and_currency() {
		FinancialTransaction transaction = builder.value("99", yen).build();
		assertEquals(yen(99), transaction.getValue());
	}

	@Test(expected = FinancialTransactionConstructionException.class)
	public void cant_change_currency_if_number_of_decimal_places_differes() {
		builder.currency(usd).value("99").currency(yen).build();
	}

}
