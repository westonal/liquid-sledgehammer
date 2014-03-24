package com.coltsoftware.liquidsledgehammer.sources;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.MoneyTestBase;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class JsonFileSourceTests extends MoneyTestBase {

	private static InputStream loadAsset(String assetName) {
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(assetName);
		return stream;
	}

	private InputStream stream;
	private FinancialTransactionSource source;

	@Before
	public void setup() {
		stream = loadAsset("statement.json");
		source = JsonStreamTransactionSource.fromStream(stream);
	}

	@After
	public void teardown() throws IOException {
		stream.close();
	}

	@Test
	public void loaded_correct_number() {
		assertEquals(2, count(source));
	}

	@Test
	public void can_read_values() throws IOException {
		Iterator<FinancialTransaction> iterator = source.iterator();
		FinancialTransaction transaction = iterator.next();
		assertEquals(local(987), transaction.getValue());
		transaction = iterator.next();
		assertEquals(local(912), transaction.getValue());
	}

	@Test
	public void can_read_descriptions() throws IOException {
		Iterator<FinancialTransaction> iterator = source.iterator();
		FinancialTransaction transaction = iterator.next();
		assertEquals("Desc for item 1", transaction.getDescription());
		transaction = iterator.next();
		assertEquals("Desc for item 2", transaction.getDescription());
	}
}
