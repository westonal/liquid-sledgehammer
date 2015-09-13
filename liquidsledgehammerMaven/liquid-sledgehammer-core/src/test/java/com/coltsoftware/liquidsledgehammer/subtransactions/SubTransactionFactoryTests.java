package com.coltsoftware.liquidsledgehammer.subtransactions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.UnassignedValueStrategy;

public class SubTransactionFactoryTests extends MoneyTestBase {

	private Builder builder;
	private SubTransactionFactory stf;
	private UnassignedValueStrategy strategy;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder()
				.source(NullFinancialTransactionSourceInformation.INSTANCE)
				.description("Desc").date(2014, 5, 1);
		stf = new SubTransactionFactory();
		strategy = mock(UnassignedValueStrategy.class);
		when(strategy.unassigned(any(FinancialTransaction.class))).thenReturn(
				"");
		stf.setUnassignedValueStrategy(strategy);
	}

	@Test
	public void can_create_sub_transactions_transaction_with_no_group() {
		Iterable<SubTransaction> subTransactions = stf
				.getSubTransactions(builder.build());
		assertEquals(0, count(subTransactions));
	}

	@Test
	public void creates_one_unassigned_sub_transactions_transaction_with_one_group() {
		Iterable<SubTransaction> subTransactions = stf
				.getSubTransactions(builder.value(usd(20)).build());
		assertEquals(1, count(subTransactions));
		assertEquals("", subTransactions.iterator().next().getGroup());
	}

	@Test
	public void refers_unassigned_to_strategy() {
		FinancialTransaction transaction = builder.value(usd(20)).build();
		stf.getSubTransactions(transaction);
		verify(strategy, times(1)).unassigned(transaction);
	}

	@Test
	public void uses_strategies_value() {
		FinancialTransaction transaction = builder.value(usd(20)).build();
		when(strategy.unassigned(transaction)).thenReturn("fromStrat");
		Iterable<SubTransaction> subTransactions = stf
				.getSubTransactions(transaction);
		assertEquals(1, count(subTransactions));
		assertEquals("fromStrat", subTransactions.iterator().next().getGroup());
	}

	@Test
	public void does_not_refers_to_strategy_when_no_remaining() {
		FinancialTransaction transaction = builder.groupPattern("x")
				.value(usd(20)).build();
		stf.getSubTransactions(transaction);
		verify(strategy, never()).unassigned(transaction);
	}

	@Test
	public void handles_null_being_set_as_strategy() {
		FinancialTransaction transaction = builder.value(usd(20)).build();
		stf.setUnassignedValueStrategy(null);
		stf.getSubTransactions(transaction);
		verify(strategy, never()).unassigned(transaction);
	}

}
