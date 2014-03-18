package com.coltsoftware.liquidsledgehammer.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class AliasPathResolverTests {

	private AliasPathResolver apr;

	@Before
	public void setup() {
		apr = new AliasPathResolver();
	}

	@Test
	public void resolve_unknown_alias() {
		assertEquals("Error.UnknownAlias.one", apr.resolve("one"));
	}

	@Test
	public void resolve_known_alias() {
		apr.put("one", "path.to.one");
		assertEquals("path.to.one", apr.resolve("one"));
	}

	@Test
	public void resolve_known_alias_and_unknown_alias() {
		apr.put("one", "path.to.one");
		assertEquals("path.to.one", apr.resolve("one"));
		assertEquals("Error.UnknownAlias.two", apr.resolve("two"));
	}

}
