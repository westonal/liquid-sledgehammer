package com.coltsoftware.liquidsledgehammer.json.streamsource;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class ReadingTests extends StreamSourceTestBase {

	@Override
	protected String getAssetName() {
		return "statement.json";
	}

	private FinancialTransactionSource source;

	@Before
	@Override
	public void setup() {
		super.setup();
		source = JsonStreamTransactionSource.fromStream(stream);
	}

	@Test
	public void loaded_correct_number() {
		assertEquals(2, count(source));
	}

	@Test
	public void can_read_values() {
		Iterator<FinancialTransaction> iterator = source.iterator();
		FinancialTransaction transaction = iterator.next();
		assertEquals(local(987), transaction.getValue());
		transaction = iterator.next();
		assertEquals(local(912), transaction.getValue());
	}

	@Test
	public void can_read_descriptions() {
		Iterator<FinancialTransaction> iterator = source.iterator();
		FinancialTransaction transaction = iterator.next();
		assertEquals("Desc for item 1", transaction.getDescription());
		transaction = iterator.next();
		assertEquals("Desc for item 2", transaction.getDescription());
	}

	@Test
	public void can_read_groups() {
		Iterator<FinancialTransaction> iterator = source.iterator();
		FinancialTransaction transaction = iterator.next();
		assertEquals("g1=4", transaction.getGroupPattern());
		transaction = iterator.next();
		assertEquals(null, transaction.getGroupPattern());
	}

	@Test
	public void can_read_dates() {
		Iterator<FinancialTransaction> iterator = source.iterator();
		FinancialTransaction transaction = iterator.next();
		assertEquals(new LocalDate(2012, 4, 13), transaction.getDate());
		transaction = iterator.next();
		assertEquals(new LocalDate(2010, 5, 29), transaction.getDate());
	}
}
