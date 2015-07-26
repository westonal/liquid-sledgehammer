package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.ContainsDescriptionStrategy;

public final class ContainsDescriptionStrategyTest {

	private ContainsDescriptionStrategy strat;

	@Before
	public void setup() {
		strat = new ContainsDescriptionStrategy();
	}

	@Test
	public void null_and_no_matchers_returns_false() {
		assertFalse(strat.matches(null));
	}

	@Test
	public void null_and_any_matchers_returns_false() {
		strat.addMatch("one");
		assertFalse(strat.matches(null));
	}

	@Test
	public void exact_match() {
		strat.addMatch("one");
		assertTrue(strat.matches("one"));
	}

	@Test
	public void in_exact_match() {
		strat.addMatch("one");
		assertTrue(strat.matches("aoneb"));
	}

	@Test
	public void two_in_exact_match() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertTrue(strat.matches("aoneb"));
	}

	@Test
	public void two_in_exact_match_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertTrue(strat.matches("atwob"));
	}

	@Test
	public void two_in_exact_match_by_case_on_second() {
		strat.addMatch("one");
		strat.addMatch("two");
		assertTrue(strat.matches("atWob"));
	}

	@Test
	public void one_in_exact_match_by_case_on_first() {
		strat.addMatch("ONE");
		strat.addMatch("two");
		assertTrue(strat.matches("a oNe"));
	}

	@Test
	public void no_match_two_items() {
		strat.addMatch("ONE");
		strat.addMatch("two");
		assertFalse(strat.matches("a"));
	}

	@Test(expected = InvalidDescriptionException.class)
	public void doesnt_allow_empty_strings() {
		strat.addMatch("");
	}

	@Test(expected = InvalidDescriptionException.class)
	public void doesnt_allow_null_strings() {
		strat.addMatch(null);
	}

	@Test(expected = InvalidDescriptionException.class)
	public void doesnt_allow_whitespace_strings() {
		strat.addMatch(" \t   ");
	}

	@Test
	public void matches_are_not_trimmed() {
		strat.addMatch(" o  ");
		assertTrue(strat.matches("a o  b"));
		assertFalse(strat.matches("a o b"));
		assertFalse(strat.matches("ao  b"));
	}

}
