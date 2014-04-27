package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.DescriptionStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.NotDescriptionStrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public final class NegatingStrategyTests {

	private DescriptionStrategy strat;
	private DescriptionStrategy negated;

	@Before
	public void setup() {
		strat = mock(DescriptionStrategy.class);
		when(strat.getGroupName()).thenReturn("groupName");
		negated = NotDescriptionStrategy.negate(strat);
	}

	@Test
	public void name_is_passed_through() {
		assertEquals("groupName", negated.getGroupName());
	}

	@Test
	public void name_is_passed_through_alternative_name() {
		when(strat.getGroupName()).thenReturn("name of group");
		assertEquals("name of group", negated.getGroupName());
	}

	@Test
	public void false_is_returned_as_true() {
		when(strat.unassigned(any(String.class))).thenReturn(true);
		assertFalse(negated.unassigned("desc"));
	}

	@Test
	public void true_is_returned_as_false() {
		when(strat.unassigned(any(String.class))).thenReturn(false);
		assertTrue(negated.unassigned("desc"));
	}

	@Test
	public void description_is_passed() {
		negated.unassigned("desc");
		verify(strat, times(1)).unassigned("desc");
	}

	@Test
	public void description_is_passed_alternative() {
		negated.unassigned("Alt");
		verify(strat, times(1)).unassigned("Alt");
	}

}
