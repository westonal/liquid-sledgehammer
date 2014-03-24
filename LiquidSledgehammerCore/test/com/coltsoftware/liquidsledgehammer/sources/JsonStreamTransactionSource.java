package com.coltsoftware.liquidsledgehammer.sources;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
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

		public String getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}
	}

	private JsonStreamTransactionSource() {
	}

	public static FinancialTransactionSource fromStream(InputStream stream) {
		FinancialTransactionList financialTransactionList = new FinancialTransactionList();

		Statement data = new Gson().fromJson(new InputStreamReader(stream),
				Statement.class);

		for (StatementEntry entry : data.getEntries())
			financialTransactionList.add(new FinancialTransaction.Builder()
					.date(2014, 1, 1).value(entry.getValue())
					.description(entry.getDescription()).build());

		return new FinancialTransactionListSourceAdapter(
				financialTransactionList);
	}

}
