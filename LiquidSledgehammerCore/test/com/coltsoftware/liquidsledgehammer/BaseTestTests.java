package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.*;

import org.junit.Test;

public final class BaseTestTests extends BaseTest {

	@Test
	public void assertNotEqual() {
		assertNotEqual(new Object(), new Object());
	}

	@Test
	public void not_equal_because_different_instances() {
		assertFalse(equals(new Object(), new Object()));
	}

	@Test
	public void equal_because_same_instance() {
		Object a = new Object();
		assertTrue(equals(a, a));
	}

	@Test
	public void not_equal_with_null() {
		assertFalse(equals(new Object(), null));
	}

	@Test
	public void not_equal_with_null_reversed_parameters() {
		assertFalse(equals(null, new Object()));
	}

	@Test
	public void equal_because_same_value() {
		String a = new String("a");
		String b = new String("a");
		assertEquals(a, b);
		assertNotSame(a, b);
		assertTrue(equals(a, b));
	}
}
