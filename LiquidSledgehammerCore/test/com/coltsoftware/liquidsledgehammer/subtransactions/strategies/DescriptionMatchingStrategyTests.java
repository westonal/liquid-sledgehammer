package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.NamedDescriptionStrategy;

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
		NamedDescriptionStrategy descStrat = mock(NamedDescriptionStrategy.class);
		strategy.add(descStrat);
		when(descStrat.getGroupName()).thenReturn("grp");
		when(descStrat.matches(any(String.class))).thenReturn(true);
		assertEquals("grp", strategy.unassigned(builder.build()));
		verify(descStrat, times(1)).matches("Desc");
	}

	@Test
	public void refers_description_to_first_and_second_child() {
		NamedDescriptionStrategy descStrat1 = mock(NamedDescriptionStrategy.class);
		NamedDescriptionStrategy descStrat2 = mock(NamedDescriptionStrategy.class);
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		strategy.unassigned(builder.build());
		verify(descStrat1, times(1)).matches("Desc");
		verify(descStrat2, times(1)).matches("Desc");
	}

	@Test
	public void if_first_child_returns_that_value_is_used() {
		NamedDescriptionStrategy descStrat1 = mock(NamedDescriptionStrategy.class);
		NamedDescriptionStrategy descStrat2 = mock(NamedDescriptionStrategy.class);
		when(descStrat1.getGroupName()).thenReturn("g1");
		when(descStrat2.getGroupName()).thenReturn("g2");
		when(descStrat1.matches(any(String.class))).thenReturn(true);
		when(descStrat2.matches(any(String.class))).thenReturn(false);
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		assertEquals("g1", strategy.unassigned(builder.build()));
		verify(descStrat1, times(1)).matches("Desc");
		verify(descStrat2, never()).matches("Desc");
	}

	@Test
	public void if_first_child_returns_null_second_child_returns() {
		NamedDescriptionStrategy descStrat1 = mock(NamedDescriptionStrategy.class);
		NamedDescriptionStrategy descStrat2 = mock(NamedDescriptionStrategy.class);
		when(descStrat1.getGroupName()).thenReturn("g1");
		when(descStrat2.getGroupName()).thenReturn("g2");
		when(descStrat2.matches(any(String.class))).thenReturn(true);
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		assertEquals("g2", strategy.unassigned(builder.build()));
		verify(descStrat1, times(1)).matches("Desc");
		verify(descStrat2, times(1)).matches("Desc");
	}

	@Test
	public void default_value_used_if_all_children_return_null() {
		NamedDescriptionStrategy descStrat1 = mock(NamedDescriptionStrategy.class);
		NamedDescriptionStrategy descStrat2 = mock(NamedDescriptionStrategy.class);
		strategy.add(descStrat1);
		strategy.add(descStrat2);
		assertEquals("", strategy.unassigned(builder.build()));
	}

	@Test
	public void alternative_description() {
		NamedDescriptionStrategy descStrat1 = mock(NamedDescriptionStrategy.class);
		when(descStrat1.getGroupName()).thenReturn("g1");
		when(descStrat1.matches(any(String.class))).thenReturn(true);
		strategy.add(descStrat1);
		assertEquals("g1",
				strategy.unassigned(builder.description("alt desc").build()));
		verify(descStrat1, times(1)).matches("alt desc");
	}
}
