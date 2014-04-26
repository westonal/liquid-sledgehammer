package com.coltsoftware.liquidsledgehammer.json;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.joda.time.LocalDate;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;
import com.google.gson.Gson;

public final class JsonStreamTransactionSource {

	private static class Statement {

		private StatementEntry[] entries;

		public StatementEntry[] getEntries() {
			return entries;
		}
	}

	private static class StatementEntry {

		private String description;

		private String value;

		private String group;

		private String date;

		public void populateBuilder(Builder builder) {
			LocalDate ldate = getLocalDate();
			builder.value(value)
					.groupPattern(group)
					.description(description)
					.date(ldate.getYear(), ldate.getMonthOfYear(),
							ldate.getDayOfMonth());
		}

		private LocalDate getLocalDate() {
			if (date == null)
				throw new JsonException();
			return LocalDate.parse(date);
		}
	}

	private JsonStreamTransactionSource() {
	}

	public static FinancialTransactionSource fromStream(InputStream stream) {
		FinancialTransactionList financialTransactionList = new FinancialTransactionList();

		Statement data = new Gson().fromJson(new InputStreamReader(stream),
				Statement.class);

		for (StatementEntry entry : data.getEntries())
			financialTransactionList.add(entryToFinancialTransaction(entry));

		return financialTransactionList;
	}

	private static FinancialTransaction entryToFinancialTransaction(
			StatementEntry entry) {
		Builder builder = new FinancialTransaction.Builder();
		entry.populateBuilder(builder);
		return builder.build();
	}
}
