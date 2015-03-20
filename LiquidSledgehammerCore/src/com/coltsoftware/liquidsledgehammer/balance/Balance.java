package com.coltsoftware.liquidsledgehammer.balance;

import java.util.Iterator;

import org.joda.time.LocalDate;

import com.coltsoftware.liquidsledgehammer.filters.TransactionDateFilter;
import com.coltsoftware.liquidsledgehammer.filters.TransactionFilter;
import com.coltsoftware.liquidsledgehammer.filters.iterator.FilteredIterator;
import com.coltsoftware.liquidsledgehammer.filters.iterator.FilteredIterator.Filter;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class Balance {

	private final FinancialTransactionSource source;

	public Balance(FinancialTransactionSource source) {
		this.source = source;
	}

	public static Balance fromTransactionSource(
			FinancialTransactionSource source) {
		return new Balance(source);
	}

	public Money getBalance() {
		Money result = Money.Zero;
		for (FinancialTransaction transaction : source)
			result = result.add(transaction.getValue());
		return result;
	}

	public Money getBalance(int year, int month, int day) {
		return getBalance(new LocalDate(year, month, day));
	}

	public Money getBalance(LocalDate localDate) {
		TransactionFilter filter = new TransactionDateFilter.Builder()
				.maximumDate(localDate).build();
		FinancialTransactionSource filtered = filterSource(source, filter);
		return fromTransactionSource(filtered).getBalance();
	}

	private static FinancialTransactionSource filterSource(
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
