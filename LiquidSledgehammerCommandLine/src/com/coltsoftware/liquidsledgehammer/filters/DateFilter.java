package com.coltsoftware.liquidsledgehammer.filters;

import java.io.PrintStream;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.coltsoftware.liquidsledgehammer.FilterFactory;
import com.coltsoftware.liquidsledgehammer.filters.TransactionDateFilter.Builder;

public final class DateFilter implements FilterFactory {

	@Override
	public TransactionFilter constructFilter(String[] filterArguments) {
		ArrayList<TransactionFilter> filters = new ArrayList<TransactionFilter>();
		for (String filter : filterArguments)
			if (filter.startsWith("d:"))
				filters.add(buildDateFilter(filter.substring(2)));
		return FilterCombine.andAll(filters);
	}

	protected static TransactionFilter buildDateFilter(String filter) {
		TransactionDateFilter.Builder builder = new TransactionDateFilter.Builder();
		if (filter.startsWith("<"))
			builder.maximumDate(getDate(filter.substring(1)));
		if (filter.startsWith(">"))
			builder.minimumDate(getDate(filter.substring(1)));
		if (filter.startsWith("=M"))
			setExactMonth(builder, filter.substring(2));
		return builder.build();
	}

	private static void setExactMonth(Builder builder, String monthYear) {
		LocalDate date = new LocalDate(monthYear + "-1");
		builder.exactMonth(date.getYear(), date.getMonthOfYear());
	}

	protected static LocalDate getDate(String filter) {
		return new LocalDate(filter);
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    d:>2015-11-13\tAfter date");
		out.println("    d:<2015-11-13\tBefore date");
		out.println("    d:=M2015-11  \tWhole month");
	}
}
