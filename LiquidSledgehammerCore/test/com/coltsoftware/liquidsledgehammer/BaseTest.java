package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.Locale;

import org.junit.Before;

public abstract class BaseTest {

	@Before
	public void setDefaultLocale() {
		Locale.setDefault(Locale.UK);
	}

	protected static void assertNotEqual(Object a, Object b) {
		assertFalse(equals(a, b));
	}

	protected static boolean equals(Object a, Object b) {
		if (a == b)
			return true;
		if (a == null)
			return false;
		return a.equals(b);
	}

	protected static int count(Iterable<?> iterable) {
		return count(iterable.iterator());
	}

	protected static int count(Iterator<?> iterator) {
		assertNotNull(iterator);
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

}
