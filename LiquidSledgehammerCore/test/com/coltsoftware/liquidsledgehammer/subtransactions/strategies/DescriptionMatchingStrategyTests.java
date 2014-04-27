package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;

public final class DescriptionMatchingStrategyTests extends MoneyTestBase {
	private Builder builder;
	private DescriptionMatchingStrategy strategy;

	@Before
	public void setup() {
		builder = new FinancialTransaction.Builder().description("Desc").date(
				2014, 5, 1);
		strategy = new DescriptionMatchingStrategy();
	}

	@Test
	public void default_value() {
		assertEquals("", strategy.unassigned(builder.build()));
	}

	@Test
	public void refers_description_to_first_child() {
		DescriptionStrategy descStrat = mock(DescriptionStrategy.class);
		strategy.add(descStrat);
		when(descStrat.unassigned(any(String.class))).thenReturn("grp");
		assertEquals("grp", strategy.unassigned(builder.build()));
		verify(descStrat, times(1)).unassigned("Desc");
	}

	@Test
	public void refers_description_to_first_and_second_child() {
		DescriptionStrategy descStrat1 = mock(DescriptionStrategy.class);
		DescriptionStrategy descStrat2 = mock(DescriptionStrategy.class);
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		strategy.unassigned(builder.build());
		verify(descStrat1, times(1)).unassigned("Desc");
		verify(descStrat2, times(1)).unassigned("Desc");
	}

	@Test
	public void if_first_child_returns_that_value_is_used() {
		DescriptionStrategy descStrat1 = mock(DescriptionStrategy.class);
		DescriptionStrategy descStrat2 = mock(DescriptionStrategy.class);
		when(descStrat1.unassigned(any(String.class))).thenReturn("g1");
		when(descStrat2.unassigned(any(String.class))).thenReturn("g2");
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		assertEquals("g1", strategy.unassigned(builder.build()));
		verify(descStrat1, times(1)).unassigned("Desc");
		verify(descStrat2, never()).unassigned("Desc");
	}

	@Test
	public void if_first_child_returns_null_second_child_returns() {
		DescriptionStrategy descStrat1 = mock(DescriptionStrategy.class);
		DescriptionStrategy descStrat2 = mock(DescriptionStrategy.class);
		when(descStrat2.unassigned(any(String.class))).thenReturn("g2");
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		assertEquals("g2", strategy.unassigned(builder.build()));
		verify(descStrat1, times(1)).unassigned("Desc");
		verify(descStrat2, times(1)).unassigned("Desc");
	}

	@Test
	public void default_value_used_if_all_children_return_null() {
		DescriptionStrategy descStrat1 = mock(DescriptionStrategy.class);
		DescriptionStrategy descStrat2 = mock(DescriptionStrategy.class);
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		assertEquals("", strategy.unassigned(builder.build()));
	}
}
