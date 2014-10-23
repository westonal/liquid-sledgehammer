package com.coltsoftware.liquidsledgehammer.filters.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class FilteredIterator<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private final Filter<T> filter;
	private boolean readHasNext;
	private T readNext;

	public interface Filter<T> {
		boolean filterTest(T item);
	}

	public FilteredIterator(Iterator<T> iterator, Filter<T> filter) {
		this.iterator = iterator;
		this.filter = filter;
	}

	@Override
	public boolean hasNext() {
		fetchNext();
		return readHasNext;
	}

	private void fetchNext() {
		while (iterator.hasNext()) {
			T next = iterator.next();
			if (filter.filterTest(next)) {
				readNext = next;
				readHasNext = true;
				return;
			}
		}
		readHasNext = false;
	}

	@Override
	public T next() {
		if (!readHasNext)
			fetchNext();
		if (readHasNext) {
			readHasNext = false;
			return readNext;
		}
		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
