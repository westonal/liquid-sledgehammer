package com.coltsoftware.liquidsledgehammer.balance;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;

public final class BalanceTests extends MoneyTestBase {

	private FinancialTransactionList ftl;

	private static FinancialTransaction makeTransaction(Money value, int year,
			int month, int day) {
		return new FinancialTransaction.Builder()
				.source(NullFinancialTransactionSourceInformation.INSTANCE)
				.date(year, month, day).value(value).build();
	}

	@Before
	public void setup() {
		ftl = new FinancialTransactionList();
	}

	@Test
	public void zero_balance() {
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getBalance());
	}

	@Test
	public void simple_balance() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(100), b.getBalance());
	}

	@Test
	public void simple_two_value_balance() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		ftl.add(makeTransaction(gbp(50), 2015, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(150), b.getBalance());
	}

	@Test
	public void transaction_source_changes_after_balance_taken() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(100), b.getBalance());
		ftl.add(makeTransaction(gbp(50), 2015, 3, 20));
		assertEquals(gbp(100), b.getBalance());
	}

	@Test
	public void balance_on_date_before_any_transactions() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getBalance(2015, 3, 19));
	}

	@Test
	public void balance_on_date_of_transation() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(100), b.getBalance(2015, 3, 20));
	}

	@Test
	public void balance_on_date_of_transation_and_previous_transactions() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2015, 3, 20));
		ftl.add(makeTransaction(gbp(40), 2015, 3, 21));
		ftl.add(makeTransaction(gbp(80), 2015, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(160), b.getBalance(2015, 3, 21));
	}

	@Test
	public void balance_on_every_day() {
		ftl.add(makeTransaction(gbp(10), 2015, 3, 19));
		ftl.add(makeTransaction(gbp(20), 2015, 3, 20));
		ftl.add(makeTransaction(gbp(40), 2015, 3, 21));
		ftl.add(makeTransaction(gbp(80), 2015, 3, 22));
		ftl.add(makeTransaction(gbp(160), 2015, 3, 23));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(0), b.getBalance(2015, 3, 18));
		assertEquals(gbp(10), b.getBalance(2015, 3, 19));
		assertEquals(gbp(30), b.getBalance(2015, 3, 20));
		assertEquals(gbp(70), b.getBalance(2015, 3, 21));
		assertEquals(gbp(150), b.getBalance(2015, 3, 22));
		assertEquals(gbp(310), b.getBalance(2015, 3, 23));
		assertEquals(gbp(310), b.getBalance(2015, 3, 24));
	}

	@Test
	public void balance_on_date_between_transactions() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2015, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(100), b.getBalance(2015, 3, 21));
	}

	@Test
	public void balance_on_date_after_transactions() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2015, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(120), b.getBalance(2015, 3, 23));
	}

	@Test
	public void balance_on_date_before_transactions() {
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2015, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getBalance(2015, 3, 10));
	}

	@Test
	public void balance_on_date_between_transactions_out_of_order() {
		ftl.add(makeTransaction(gbp(20), 2015, 3, 22));
		ftl.add(makeTransaction(gbp(100), 2015, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(100), b.getBalance(2015, 3, 21));
	}

	@Test
	public void min_date_of_empty_source() {
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(new LocalDate(), b.getMinDate());
	}

	@Test
	public void min_date_of_source() {
		ftl.add(makeTransaction(gbp(100), 2014, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2014, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(new LocalDate(2014, 3, 20), b.getMinDate());
	}

	@Test
	public void min_date_of_source_out_of_order() {
		ftl.add(makeTransaction(gbp(20), 2014, 3, 22));
		ftl.add(makeTransaction(gbp(100), 2014, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(new LocalDate(2014, 3, 20), b.getMinDate());
	}

	@Test
	public void min_date_of_source_future_dates() {
		ftl.add(makeTransaction(gbp(100), 2099, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(new LocalDate(2099, 3, 20), b.getMinDate());
	}

	@Test
	public void max_date_of_source() {
		ftl.add(makeTransaction(gbp(100), 2014, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2014, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(new LocalDate(2014, 3, 22), b.getMaxDate());
	}

	@Test
	public void max_date_of_source_out_of_order() {
		ftl.add(makeTransaction(gbp(20), 2014, 3, 22));
		ftl.add(makeTransaction(gbp(100), 2014, 3, 20));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(new LocalDate(2014, 3, 22), b.getMaxDate());
	}

	@Test
	public void max_date_of_source_future_dates() {
		ftl.add(makeTransaction(gbp(100), 2099, 3, 20));
		ftl.add(makeTransaction(gbp(20), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(new LocalDate(2099, 3, 22), b.getMaxDate());
	}

	@Test
	public void min_and_max_date_of_empty_source_are_same() {
		Balance b = Balance.fromTransactionSource(ftl);
		assertSame(b.getMinDate(), b.getMaxDate());
	}

	@Test
	public void min_value_of_empty_source() {
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getMinValue());
	}

	@Test
	public void max_value_of_empty_source() {
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getMaxValue());
	}

	@Test
	public void min_value_of_single_entry_positive_source() {
		ftl.add(makeTransaction(gbp(20), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getMinValue());
	}

	@Test
	public void max_value_of_single_entry_positive_source() {
		ftl.add(makeTransaction(gbp(20), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(20), b.getMaxValue());
	}

	@Test
	public void min_value_of_single_entry_negative_source() {
		ftl.add(makeTransaction(gbp(-20), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(-20), b.getMinValue());
	}

	@Test
	public void max_value_of_single_entry_negative_source() {
		ftl.add(makeTransaction(gbp(-20), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getMaxValue());
	}

	@Test
	public void min_value_of_double_entry_positive_source() {
		ftl.add(makeTransaction(gbp(20), 2099, 3, 22));
		ftl.add(makeTransaction(gbp(30), 2099, 3, 23));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getMinValue());
	}

	@Test
	public void max_value_of_double_entry_positive_source() {
		ftl.add(makeTransaction(gbp(20), 2099, 3, 22));
		ftl.add(makeTransaction(gbp(30), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(50), b.getMaxValue());
	}

	@Test
	public void min_value_of_double_entry_negative_source() {
		ftl.add(makeTransaction(gbp(-20), 2099, 3, 22));
		ftl.add(makeTransaction(gbp(-30), 2099, 3, 23));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(-50), b.getMinValue());
	}

	@Test
	public void max_value_of_double_entry_negative_source() {
		ftl.add(makeTransaction(gbp(-20), 2099, 3, 22));
		ftl.add(makeTransaction(gbp(-30), 2099, 3, 22));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(Money.Zero, b.getMaxValue());
	}

	@Test
	public void min_max_value_of_double_entry_mixed_source() {
		ftl.add(makeTransaction(gbp(20), 2099, 3, 22));
		ftl.add(makeTransaction(gbp(-30), 2099, 3, 23));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(-10), b.getMinValue());
		assertEquals(gbp(20), b.getMaxValue());
	}

	@Test
	public void min_max_value_of_double_entry_mixed_source_negative_first() {
		ftl.add(makeTransaction(gbp(-20), 2099, 3, 22));
		ftl.add(makeTransaction(gbp(30), 2099, 3, 23));
		Balance b = Balance.fromTransactionSource(ftl);
		assertEquals(gbp(-20), b.getMinValue());
		assertEquals(gbp(10), b.getMaxValue());
	}

}
