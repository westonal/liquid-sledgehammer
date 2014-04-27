package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.ContainsDescriptionStrategy;

public final class ContainsDescriptionStrategyTest {

	private ContainsDescriptionStrategy strat;

	@Before
	public void setup() {
		strat = new ContainsDescriptionStrategy("groupName");
	}

	@Test
	public void null_and_no_matchers_returns_false() {
		assertFalse(strat.unassigned(null));
	}

	@Test
	public void null_and_any_matchers_returns_false() {
		strat.addMatch("one");
		assertFalse(strat.unassigned(null));
	}

	@Test
	public void exact_match() {
		strat.addMatch("one");
		assertTrue(strat.unassigned("one"));
	}

	@Test
	public void in_exact_match() {
		strat.addMatch("one");
		assertTrue(strat.unassigned("aoneb"));
	}

	@Test
	public void two_in_exact_match() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertTrue(strat.unassigned("aoneb"));
	}

	@Test
	public void two_in_exact_match_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertTrue(strat.unassigned("atwob"));
	}

	@Test
	public void two_in_exact_match_by_case_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertTrue(strat.unassigned("atWob"));
	}

	@Test
	public void one_in_exact_match_by_case_on_first() {
		strat.addMatch("ONE");
		strat.addMatch("two");
		assertTrue(strat.unassigned("a oNe"));
	}

	@Test
	public void no_match_two_items() {
		strat.addMatch("ONE");
		strat.addMatch("two");
		assertFalse(strat.unassigned("a"));
	}

	@Test
	public void can_read_group_name() {
		assertEquals("groupName", strat.getGroupName());
	}

	@Test
	public void can_read_group_name_alternative() {
		assertEquals("the group name", new ContainsDescriptionStrategy(
				"the group name").getGroupName());
	}

}
