package com.coltsoftware.liquidsledgehammer.model;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.BaseTest;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public final class SubTransactionTests extends BaseTest{

	private Builder builder;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().date(2014, 5, 1);
	}

	@Test
	public void can_get_single_sub_transaction() {
		FinancialTransaction transaction = builder.value(10000).build();
		assertEquals(1, count(transaction.getSubTransactions()));
	}
	
	@Test
	public void can_get_single_sub_transaction_and_has_same_value() {
		FinancialTransaction transaction = builder.value(10000).build();
		assertEquals(transaction.getValue(), transaction.getSubTransactions().iterator().next().getValue());
	}

}
