package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.NullUnassignedStrategy;

public final class NullUnassignedStrategyTests extends MoneyTestBase{

	private NullUnassignedStrategy strategy;

	@Before
	public void setup() {
		strategy = NullUnassignedStrategy.INSTANCE;
	}

	@Test
	public void returns_empty_string_for_null() {
		assertEquals("", strategy.unassigned(null));
	}
	
	@Test
	public void returns_empty_string_for_non_null() {
		FinancialTransaction transaction = new FinancialTransaction.Builder().date(2014, 5, 1).value(usd(20)).build();
		assertEquals("", strategy.unassigned(transaction));
	}

}
