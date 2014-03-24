package com.coltsoftware.liquidsledgehammer.sources;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction.Builder;
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

		public void populateBuilder(Builder builder) {
			builder.value(value).groupPattern(group).description(description);
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

		return new FinancialTransactionListSourceAdapter(
				financialTransactionList);
	}

	private static FinancialTransaction entryToFinancialTransaction(
			StatementEntry entry) {
		Builder builder = new FinancialTransaction.Builder().date(2014, 1, 1);
		entry.populateBuilder(builder);
		return builder.build();
	}
}
