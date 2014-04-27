package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class NotContainsDescriptionStrategyTest {

	private ContainsDescriptionStrategy strat;
	private DescriptionStrategy testStrat;

	@Before
	public void setup() {
		strat = new ContainsDescriptionStrategy("groupName");
		testStrat = NotDescriptionStrategy.negate(strat);
	}

	@Test
	public void null_and_no_matchers_returns_false() {
		assertTrue(testStrat.unassigned(null));
	}

	@Test
	public void null_and_any_matchers_returns_false() {
		strat.addMatch("one");
		assertTrue(testStrat.unassigned(null));
	}

	@Test
	public void exact_match() {
		strat.addMatch("one");
		assertFalse(testStrat.unassigned("one"));
	}

	@Test
	public void in_exact_match() {
		strat.addMatch("one");
		assertFalse(testStrat.unassigned("aoneb"));
	}

	@Test
	public void two_in_exact_match() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertFalse(testStrat.unassigned("aoneb"));
	}

	@Test
	public void two_in_exact_match_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertFalse(testStrat.unassigned("atwob"));
	}

	@Test
	public void two_in_exact_match_by_case_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertFalse(testStrat.unassigned("atWob"));
	}

	@Test
	public void one_in_exact_match_by_case_on_first() {
		strat.addMatch("ONE");
		strat.addMatch("two");
		assertFalse(testStrat.unassigned("a oNe"));
	}

}
