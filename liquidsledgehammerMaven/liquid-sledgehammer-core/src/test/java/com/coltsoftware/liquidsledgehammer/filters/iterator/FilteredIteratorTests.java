package com.coltsoftware.liquidsledgehammer.filters.iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.filters.iterator.FilteredIterator.Filter;

public final class FilteredIteratorTests {

	private Filter<String> aToZ;
	private ArrayList<String> allLettersList;

	@Before
	public void setup() {
		aToZ = new Filter<String>() {
			@Override
			public boolean filterTest(String item) {
				return item == null || item.matches("[A-Z]");
			}
		};

		allLettersList = new ArrayList<String>();
		allLettersList.add("A");
		allLettersList.add("B");
		allLettersList.add("C");
	}

	private FilteredIterator<String> createFilteredIterator(String... list) {
		return new FilteredIterator<String>(Arrays.asList(list).iterator(),
				aToZ);
	}

	@Test
	public void has_next_returns_false_for_empty_list() {
		Iterator<String> filtered = createFilteredIterator();
		assertFalse(filtered.hasNext());
	}

	@Test
	public void has_next_returns_true_for_simple_list() {
		Iterator<String> filtered = createFilteredIterator("A");
		assertTrue(filtered.hasNext());
	}

	@Test
	public void has_next_returns_false_for_simple_list_of_numbers() {
		Iterator<String> filtered = createFilteredIterator("1");
		assertFalse(filtered.hasNext());
	}

	@Test
	public void has_next_returns_true_for_simple_list_with_number_first() {
		Iterator<String> filtered = createFilteredIterator("1", "A");
		assertTrue(filtered.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void next_throws_if_no_matching() {
		Iterator<String> filtered = createFilteredIterator("1");
		filtered.next();
	}

	@Test
	public void next_returns_item_for_simple_list() {
		Iterator<String> filtered = createFilteredIterator("A");
		assertEquals("A", filtered.next());
	}

	@Test
	public void next_returns_item_for_simple_list_after_has_next() {
		Iterator<String> filtered = createFilteredIterator("A");
		assertTrue(filtered.hasNext());
		assertEquals("A", filtered.next());
	}

	@Test
	public void list_can_contain_null() {
		Iterator<String> filtered = createFilteredIterator((String) null);
		assertTrue(filtered.hasNext());
		assertNull(filtered.next());
	}

	@Test
	public void next_skips_number() {
		Iterator<String> filtered = createFilteredIterator("1", "A");
		assertEquals("A", filtered.next());
	}

	@Test
	public void next_skips_numbers() {
		Iterator<String> filtered = createFilteredIterator("1", "A", "2", "B");
		assertEquals("A", filtered.next());
		assertEquals("B", filtered.next());
	}

	@Test
	public void next_skips_numbers_with_has_next_tests() {
		Iterator<String> filtered = createFilteredIterator("A", "1", "B", "2");
		assertTrue(filtered.hasNext());
		assertEquals("A", filtered.next());
		assertTrue(filtered.hasNext());
		assertEquals("B", filtered.next());
		assertFalse(filtered.hasNext());
	}

	@Test
	public void next_skips_numbers_at_end() {
		Iterator<String> filtered = createFilteredIterator("A", "1", "B", "2");
		assertEquals("A", filtered.next());
		assertEquals("B", filtered.next());
	}

	@Test
	public void next_skips_numbers_at_end_with_has_next_tests() {
		Iterator<String> filtered = createFilteredIterator("A", "1", "B", "2");
		assertTrue(filtered.hasNext());
		assertEquals("A", filtered.next());
		assertTrue(filtered.hasNext());
		assertEquals("B", filtered.next());
		assertFalse(filtered.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void next_skips_numbers_next_throws() {
		Iterator<String> filtered = createFilteredIterator("1", "A", "2", "B");
		assertEquals("A", filtered.next());
		assertEquals("B", filtered.next());
		filtered.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void next_skips_numbers_at_end_and_next_throws() {
		Iterator<String> filtered = createFilteredIterator("A", "1", "B", "2");
		assertEquals("A", filtered.next());
		assertEquals("B", filtered.next());
		filtered.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void next_throws_if_called_after_unsuccessful_has_next() {
		Iterator<String> filtered = createFilteredIterator("1");
		assertFalse(filtered.hasNext());
		filtered.next();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void remove_throws() {
		Iterator<String> filtered = createFilteredIterator();
		filtered.remove();
	}

}
