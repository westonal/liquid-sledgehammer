package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.DescriptionStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.NotDescriptionStrategy;

public final class NegatingStrategyTests {

	private DescriptionStrategy strat;
	private DescriptionStrategy negated;

	@Before
	public void setup() {
		strat = mock(DescriptionStrategy.class);
		negated = NotDescriptionStrategy.negate(strat);
	}

	@Test
	public void false_is_returned_as_true() {
		when(strat.matches(any(String.class))).thenReturn(true);
		assertFalse(negated.matches("desc"));
	}

	@Test
	public void true_is_returned_as_false() {
		when(strat.matches(any(String.class))).thenReturn(false);
		assertTrue(negated.matches("desc"));
	}

	@Test
	public void description_is_passed() {
		negated.matches("desc");
		verify(strat, times(1)).matches("desc");
	}

	@Test
	public void description_is_passed_alternative() {
		negated.matches("Alt");
		verify(strat, times(1)).matches("Alt");
	}

}
