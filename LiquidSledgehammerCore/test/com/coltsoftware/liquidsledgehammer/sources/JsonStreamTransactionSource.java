package com.coltsoftware.liquidsledgehammer.sources;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.google.gson.Gson;

public final class JsonStreamTransactionSource {

	public class MyClass {

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	private JsonStreamTransactionSource() {
	}

	public static FinancialTransactionSource fromStream(InputStream stream) {
		FinancialTransactionList financialTransactionList = new FinancialTransactionList();

		MyClass data = new Gson().fromJson(new InputStreamReader(stream),
				MyClass.class);

		financialTransactionList.add(new FinancialTransaction.Builder()
				.date(2014, 1, 1).value(data.getValue()).build());

		return new FinancialTransactionListSourceAdapter(
				financialTransactionList);
	}

}
