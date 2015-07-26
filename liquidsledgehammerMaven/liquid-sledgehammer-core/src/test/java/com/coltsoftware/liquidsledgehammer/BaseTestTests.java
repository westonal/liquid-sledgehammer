package com.coltsoftware.liquidsledgehammer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

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

	@Test
	public void can_count_iterator() {
		assertEquals(0, count(new ArrayList<Object>().iterator()));
	}

	@Test
	public void can_count_iteratable() {
		assertEquals(0, count(new ArrayList<Object>()));
	}

	@Test
	public void can_count_iterator_1() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(1);
		assertEquals(1, count(arrayList.iterator()));
	}

	@Test
	public void can_count_iteratable_1() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(1);
		assertEquals(1, count(arrayList));
	}

	@Test
	public void can_count_iterator_2() {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		arrayList.add(10f);
		arrayList.add(20f);
		assertEquals(2, count(arrayList.iterator()));
	}

	@Test
	public void can_count_iteratable_2() {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		arrayList.add(10f);
		arrayList.add(20f);
		assertEquals(2, count(arrayList));
	}
}
