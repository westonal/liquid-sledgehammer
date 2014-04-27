package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class ContainsDescriptionStrategyTest {

	private ContainsDescriptionStrategy strat;

	@Before
	public void setup() {
		strat = new ContainsDescriptionStrategy("groupName");
	}

	@Test
	public void null_and_no_matchers_returns_null() {
		assertNull(strat.unassigned(null));
	}

	@Test
	public void null_and_any_matchers_returns_null() {
		strat.addMatch("one");
		assertNull(strat.unassigned(null));
	}

	@Test
	public void exact_match() {
		strat.addMatch("one");
		assertEquals("groupName", strat.unassigned("one"));
	}

	@Test
	public void in_exact_match() {
		strat.addMatch("one");
		assertEquals("groupName", strat.unassigned("aoneb"));
	}

	@Test
	public void two_in_exact_match() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertEquals("groupName", strat.unassigned("aoneb"));
	}
	
	@Test
	public void two_in_exact_match_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertEquals("groupName", strat.unassigned("atwob"));
	}
	
	@Test
	public void two_in_exact_match_by_case_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertEquals("groupName", strat.unassigned("atWob"));
	}
	
	@Test
	public void one_in_exact_match_by_case_on_first() {
		strat.addMatch("ONE");
		strat.addMatch("two");
		assertEquals("groupName", strat.unassigned("a oNe"));
	}

}
