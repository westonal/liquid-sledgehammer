package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.assertFalse;

public abstract class BaseTest {

	protected static void assertNotEqual(Object a, Object b) {
		assertFalse(equals(a, b));
	}

	private static boolean equals(Object a, Object b) {
		if (a == b)
			return true;
		if (a == null)
			return false;
		return a.equals(b);
	}

}
