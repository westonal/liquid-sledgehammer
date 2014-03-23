package com.coltsoftware.liquidsledgehammer.sources;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	public void can_load_file() throws IOException {
		InputStream stream = loadAsset("statement.json");
		try {
			FinancialTransactionSource source = JsonStreamTransactionSource
					.fromStream(stream);
			assertEquals(1, count(source));
			FinancialTransaction transaction = source.iterator().next();
			assertEquals(local(987), transaction.getValue());
		} finally {
			stream.close();
		}
	}
}
