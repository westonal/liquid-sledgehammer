package com.coltsoftware.liquidsledgehammer;

import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.filters.TransactionFilter;
import com.coltsoftware.liquidsledgehammer.filters.iterator.FilteredIterator;
import com.coltsoftware.liquidsledgehammer.filters.iterator.FilteredIterator.Filter;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class FilterHelper {
	private FilterHelper() {
	}

	public static FinancialTransactionSource filterSource(
			final FinancialTransactionSource source,
			final TransactionFilter filter) {
		return new FinancialTransactionSource() {

			@Override
			public Iterator<FinancialTransaction> iterator() {

				return new FilteredIterator<FinancialTransaction>(
						source.iterator(), new Filter<FinancialTransaction>() {

							@Override
							public boolean filterTest(FinancialTransaction item) {
								return filter.allow(item);
							}
						});
			}
		};
	}
}
