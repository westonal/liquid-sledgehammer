package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public final class DescriptionStrategyNamerTests {

	private DescriptionStrategy strat;
	private NamedDescriptionStrategy namedStrat;

	@Before
	public void setup() {
		strat = mock(DescriptionStrategy.class);
		namedStrat = DescriptionStrategyNamer.name("group", strat);
	}

	@Test
	public void can_name() {
		assertEquals("group", namedStrat.getGroupName());
	}

	@Test
	public void can_name_alternative() {
		assertEquals("alt Name",
				DescriptionStrategyNamer.name("alt Name", strat).getGroupName());
	}

	@Test
	public void passes_calls_though() {
		namedStrat.matches("D1");
		verify(strat, times(1)).matches("D1");
		verify(strat, times(1)).matches(any(String.class));
	}

	@Test
	public void passes_calls_though_alternaive_description() {
		namedStrat.matches("D2");
		verify(strat, times(1)).matches("D2");
		verify(strat, times(1)).matches(any(String.class));
	}

	@Test
	public void return_value_used_true() {
		when(strat.matches(any(String.class))).thenReturn(true);
		assertTrue(namedStrat.matches("Desc"));
	}

	@Test
	public void return_value_used_false() {
		when(strat.matches(any(String.class))).thenReturn(false);
		assertFalse(namedStrat.matches("Desc"));
	}

}
