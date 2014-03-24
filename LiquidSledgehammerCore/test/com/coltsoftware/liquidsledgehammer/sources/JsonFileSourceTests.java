package com.coltsoftware.liquidsledgehammer.sources;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class JsonFileSourceTests extends MoneyTestBase {

	private static InputStream loadAsset(String assetName) {
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(assetName);
		return stream;
	}

	@Test
	public void test_load_asset() throws IOException {
		InputStream stream = loadAsset("statement.json");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		for (String line = reader.readLine(); line != null; line = reader
				.readLine())
			System.out.println(line);
		stream.close();
	}

	@Test
	public void can_load_file_and_read_values() throws IOException {
		InputStream stream = loadAsset("statement.json");
		try {
			FinancialTransactionSource source = JsonStreamTransactionSource
					.fromStream(stream);
			assertEquals(2, count(source));
			Iterator<FinancialTransaction> iterator = source.iterator();
			FinancialTransaction transaction = iterator.next();
			assertEquals(local(987), transaction.getValue());
			transaction = iterator.next();
			assertEquals(local(912), transaction.getValue());
		} finally {
			stream.close();
		}
	}

	@Test
	public void can_load_file_and_read_descriptions() throws IOException {
		InputStream stream = loadAsset("statement.json");
		try {
			FinancialTransactionSource source = JsonStreamTransactionSource
					.fromStream(stream);
			assertEquals(2, count(source));
			Iterator<FinancialTransaction> iterator = source.iterator();
			FinancialTransaction transaction = iterator.next();
			assertEquals("Desc for item 1", transaction.getDescription());
			transaction = iterator.next();
			assertEquals("Desc for item 2", transaction.getDescription());
		} finally {
			stream.close();
		}
	}
}
