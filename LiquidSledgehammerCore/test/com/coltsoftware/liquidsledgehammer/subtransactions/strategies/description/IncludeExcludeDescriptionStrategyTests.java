package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class IncludeExcludeDescriptionStrategyTests {

	private IncludeExcludeDescriptionStrategy strat;

	@Before
	public void setup() {
		strat = new IncludeExcludeDescriptionStrategy("name");
	}

	@Test
	public void default_value() {
		assertFalse(strat.unassigned("a"));
	}

	@Test
	public void simple_includes() {
		strat.addInclude("abc");
		assertTrue(strat.unassigned("abc"));
	}

	@Test
	public void simple_includes_false() {
		strat.addInclude("abc");
		assertFalse(strat.unassigned("a"));
	}

	@Test
	public void simple_includes_two() {
		strat.addInclude("abc");
		strat.addInclude("def");
		assertTrue(strat.unassigned("abc"));
	}
	
	@Test
	public void simple_includes_two_substring() {
		strat.addInclude("abc");
		strat.addInclude("def");
		assertTrue(strat.unassigned("-abcd"));
	}
	
	@Test
	public void simple_includes_excludes_substring() {
		strat.addInclude("abc");
		strat.addInclude("def");
		strat.addExclude("-abcd");
		assertFalse(strat.unassigned("-abcd"));
	}

}
